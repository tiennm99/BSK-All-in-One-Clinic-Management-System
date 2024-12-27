package BsK.client.ui.handler;

import BsK.client.network.handler.ClientHandler;
import BsK.common.packet.Packet;
import BsK.common.packet.req.LoginRequest;
import BsK.common.packet.res.HandshakeCompleteResponse;
import BsK.common.packet.res.LoginSuccessResponse;
import BsK.common.util.network.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;

@Slf4j
public class UIHandler {
    public static final UIHandler INSTANCE = new UIHandler();
    boolean isLoggedIn = false;
    int id = -1;
    boolean readyToLogin = false;

    public void onPacket(Packet packet) {
        switch (packet) {
            case HandshakeCompleteResponse response:
                System.out.println("Received handshake complete response");
                readyToLogin = true;
                break;
            case LoginSuccessResponse response:
                log.info("Received login success response: " + response.getUserId() + " " + response.getRole());
                break;
            default:
                return;
        }
    }

    public void showUI() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
             var login = new LoginRequest(username, password);
             NetworkUtil.sendPacket(ClientHandler.ctx.channel(), login);
        } else {
            System.out.println("Login canceled");
        }
    }

//    public void onClickButtonLogin() {
//        if (!readyToLogin) {
//            // TODO: hiển thị lỗi "Chưa kết nối tới server"
//            return;
//        }
//        var username = "username"; // Đọc từ đâu đó
//        var password = "password"; // Đọc từ đâu đó
//
//        NetworkUtil.sendPacket(ctx.channel(), login);
//        NetworkUtil.sendPacket(loginRequest);
//    }
}