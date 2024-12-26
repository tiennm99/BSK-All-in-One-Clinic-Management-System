package BsK.client.network.handler;

import BsK.common.packet.Packet;
import BsK.common.packet.PacketSerializer;
import BsK.common.packet.req.GetPatientInfoReq;
import BsK.common.packet.req.LoginRequest;
import BsK.common.packet.res.ErrorResponse;
import BsK.common.packet.res.GetPatientInfoRes;
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

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
    log.debug("Received message: {}", frame.text());
    var packet = PacketSerializer.GSON.fromJson(frame.text(), Packet.class);
    switch (packet) {
      case HandshakeCompleteResponse handshakeCompleteResponse -> {
        log.info("Handshake complete");
        var login = new LoginRequest("user", "user");
        NetworkUtil.sendPacket(ctx.channel(), login);
      }
      case ErrorResponse response -> {
        log.error("Received error response: {}", response.getError());
        switch (response.getError()) {
          case USER_ALREADY_EXISTS -> log.error("User already exists");
          default -> log.error("Received error response: {}", response.getError());
        }
      }
      case LoginSuccessResponse loginSuccessResponse -> {
        log.info("Received login success response, id {}, rold {}", loginSuccessResponse.getId(), loginSuccessResponse.getRole());

        // Chỉ để demo, chỉ nên request khi mà user click lấy list user hay gì đó
        var getPatientInfoReq = new GetPatientInfoReq("");
        NetworkUtil.sendPacket(ctx.channel(), getPatientInfoReq);
      }
      case GetPatientInfoRes getPatientInfoRes -> {
        log.info("Received get patient info {}", PacketSerializer.GSON.toJson(getPatientInfoRes));
      }
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
