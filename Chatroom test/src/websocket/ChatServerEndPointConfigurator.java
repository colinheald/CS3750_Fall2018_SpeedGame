package websocket;

import javax.websocket.server.ServerEndpointConfig.Configurator;

public class ChatServerEndPointConfigurator extends Configurator {
 
    private static ChatroomServerEndpoint chatServer = new ChatroomServerEndpoint();
 
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T)chatServer;
    }
}
