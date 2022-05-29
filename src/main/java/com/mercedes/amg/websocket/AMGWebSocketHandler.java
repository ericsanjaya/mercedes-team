package com.mercedes.amg.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.amg.factory.ObjectMapFactory;
import com.mercedes.amg.dto.VehicleStatusDTO;
import com.mercedes.amg.model.VehicleStatus;
import com.mercedes.amg.repository.VehicleStatusRepository;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class AMGWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {
    private static final Logger logger = LoggerFactory.getLogger(AMGWebSocketHandler.class);

    @Autowired
    private VehicleStatusRepository vehicleStatusRepository;

    private final Set<WebSocketSession> sessionsCar = new CopyOnWriteArraySet<>();
    private final Set<WebSocketSession> sessionsDashboard = new CopyOnWriteArraySet<>();

    private final ConcurrentHashMap<String, Object> lastVehicleStatus = new ConcurrentHashMap<>();

    private final ObjectPool<ObjectMapper> objMapperPool;

    ExecutorService pool = Executors.newFixedThreadPool(150);

    public AMGWebSocketHandler() {
        var objectMapFactory = new ObjectMapFactory();
        var objectMapperGenericObjectPoolConfig = new GenericObjectPoolConfig<ObjectMapper>();
        objectMapperGenericObjectPoolConfig.setMinIdle(150);
        objectMapperGenericObjectPoolConfig.setMaxIdle(2000);
        objectMapperGenericObjectPoolConfig.setMaxTotal(5000);
        this.objMapperPool = new GenericObjectPool<>(objectMapFactory, objectMapperGenericObjectPoolConfig);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Server connection opened");

        var type = (String) session.getAttributes().get("type");
        switch (type.toUpperCase()) {
            case "CAR": {
                sessionsCar.add(session);
                break;
            }
            case "DASHBOARD": {
                sessionsDashboard.add(session);
                break;
            }
            default:
                break;
        }

        TextMessage message = new TextMessage("All engine set");
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Server connection closed: {}", status);
        var type = (String) session.getAttributes().get("type");
        switch (type.toUpperCase()) {
            case "CAR": {
                sessionsCar.remove(session);
            }
            case "DASHBOARD": {
                sessionsDashboard.remove(session);
            }
            default:
                break;
        }
    }

    @Scheduled(fixedRate = 10)
    void sendPeriodicMessages() throws IOException {
        ObjectMapper objectMapper;
        try {
            objectMapper = this.objMapperPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (WebSocketSession session : sessionsDashboard) {
            var vehicleId = (String) session.getAttributes().get("vehicleId");
            var vehicleStatus = lastVehicleStatus.get(vehicleId);
            var json = objectMapper.writeValueAsString(vehicleStatus);

            if (session.isOpen()) {
                session.sendMessage(new TextMessage(json));
            }
        }

        try {
            this.objMapperPool.returnObject(objectMapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        ObjectMapper objectMapper;
        try {
            objectMapper = this.objMapperPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Read JSON String to POJO
        var vehicleStatusDTO = objectMapper.readValue(message.getPayload(), VehicleStatusDTO.class);

        //  Store the last Vehicle Status in memory
        var newLastVehicleStatus = new VehicleStatus(vehicleStatusDTO.getVehicleId(), vehicleStatusDTO.getSpeed(), vehicleStatusDTO.getBrakeCondition(), vehicleStatusDTO.getGear());
        lastVehicleStatus.put(vehicleStatusDTO.getVehicleId(), newLastVehicleStatus);

        // Store VehicleStatus to Persistence Storage
        var newStatus = new VehicleStatus(vehicleStatusDTO.getVehicleId(), vehicleStatusDTO.getSpeed(), vehicleStatusDTO.getBrakeCondition(), vehicleStatusDTO.getGear());
        CompletableFuture.runAsync(() -> vehicleStatusRepository.save(newStatus), pool);

        try {
            this.objMapperPool.returnObject(objectMapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.info("Server transport error: {}", exception.getMessage());
    }

    @Override
    public List<String> getSubProtocols() {
        return List.of("mercedes-net");
    }
}
