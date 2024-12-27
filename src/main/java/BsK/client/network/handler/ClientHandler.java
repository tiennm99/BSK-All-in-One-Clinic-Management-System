package BsK.client.network.handler;

import BsK.client.ui.handler.UIHandler;
import BsK.common.packet.Packet;
import BsK.common.packet.PacketSerializer;
import BsK.common.packet.req.LoginRequest;
import BsK.common.packet.res.ErrorResponse;
import BsK.common.packet.res.HandshakeCompleteResponse;
import BsK.common.packet.res.LoginSuccessResponse;
import BsK.common.util.network.NetworkUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
  public static ChannelHandlerContext ctx;
  public static final ClientHandler INSTANCE = new ClientHandler();
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
    log.debug("Received message: {}", frame.text());
    var packet = PacketSerializer.GSON.fromJson(frame.text(), Packet.class);
    switch (packet) {
      case HandshakeCompleteResponse handshakeCompleteResponse -> {
        log.info("Handshake complete");
        this.ctx = ctx;
//        var login = new LoginRequest("user", "user");
//        NetworkUtil.sendPacket(ctx.channel(), login);
      }
      case ErrorResponse response -> {
        log.error("Received error response: {}", response.getError());
        switch (response.getError()) {
          case USER_ALREADY_EXISTS -> log.error("User already exists");
          default -> log.error("Received error response: {}", response.getError());
        }
      }
      case LoginSuccessResponse loginSuccessResponse -> log.info("Received login success response");
      case null, default -> log.warn("Unknown message: {}", frame.text());
    }
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent event = (IdleStateEvent) evt;
      if (event.state() == IdleState.WRITER_IDLE) {
        ctx.writeAndFlush(new PingWebSocketFrame());
      }
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    if (cause instanceof IOException) {
      log.error("Connection closed");
      System.exit(0);
    }
  }
}
