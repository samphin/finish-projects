package com.ryit.gateway.filter;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 路由转发过滤器
 *
 * @author samphin
 * @since 2019-10-30 11:26:45
 */
@Slf4j
public class ForwardGatewayFilterFactory extends AbstractGatewayFilterFactory<ForwardGatewayFilterFactory.Config> {

    public ForwardGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * 解决gateway报错：Only one connection receive subscriber allowed
     *
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            URI uri = exchange.getRequest().getURI();
            URI ex = UriComponentsBuilder.fromUri(uri).build(true).toUri();
            ServerHttpRequest request = exchange.getRequest().mutate().uri(ex).build();
            //获取请求内容
            Flux<DataBuffer> body = request.getBody();
            //缓存读取的request body信息
            AtomicReference<String> bodyRef = new AtomicReference<>();
            body.subscribe(dataBuffer -> {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
                DataBufferUtils.release(dataBuffer);
                bodyRef.set(charBuffer.toString());
            });
            //获取request body
            String bodyStr = bodyRef.get();
            //打印请求参数
            log.info(bodyStr);

            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);

            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

            request = new ServerHttpRequestDecorator(request) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
            /**
             * 封装我们的request
             */
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    /**
     * 生成数据缓冲对象
     *
     * @param value
     * @return
     */
    protected DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * 自定义路由配置
     */
    public static class Config {
    }
}
