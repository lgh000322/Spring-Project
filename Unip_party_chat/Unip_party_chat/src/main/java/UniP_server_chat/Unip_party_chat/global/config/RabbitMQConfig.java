package UniP_server_chat.Unip_party_chat.global.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory batchMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setBatchListener(true);  // 배치 처리를 활성화
        factory.setBatchSize(10);  // 한 번에 가져올 메시지 개수 설정
        factory.setConsumerBatchEnabled(true);  // 컨슈머 배치 활성화
        return factory;
    }

    @Bean
    public Queue storageQueue() {
        return QueueBuilder.durable("chat.storage.queue")
                .withArgument("x-queue-mode", "lazy")
                .build();
    }

    // Broadcast Exchange 설정
    @Bean
    public FanoutExchange broadcastExchange() {
        return new FanoutExchange("chat.broadcast.exchange");
    }

    // 각 서버별 Unique한 브로드캐스트 수신 큐 생성
    @Bean
    public Queue broadcastQueue() {
        return QueueBuilder.nonDurable(
                        "chat.broadcast." + UUID.randomUUID())
                .autoDelete()
                .build();
    }

    @Bean
    public Binding broadcastBinding(Queue broadcastQueue,
                                    FanoutExchange broadcastExchange) {
        return BindingBuilder.bind(broadcastQueue)
                .to(broadcastExchange);
    }

}
