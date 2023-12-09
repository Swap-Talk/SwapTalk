import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplicationFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton friendsButton, chatButton, settingsButton;

    public MainApplicationFrame() {
        // 메인 프레임 설정
        setTitle("SwapTalk");
        setSize(390, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 각 화면 패널을 추가
        FriendListPanel friendListPanel = new FriendListPanel();
        ChatListPanel chatListPanel = new ChatListPanel();
        SettingsPanel settingsPanel = new SettingsPanel();

        cardPanel.add(friendListPanel, "Friends");
        cardPanel.add(chatListPanel, "Chats");
        cardPanel.add(settingsPanel, "Settings");

     // 왼쪽에 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        friendsButton = new JButton(new ImageIcon("/Users/jinwoo/Desktop/User_fill.png"));
        chatButton = new JButton(new ImageIcon("/Users/jinwoo/Desktop/Subtract.png"));
        settingsButton = new JButton(new ImageIcon("/Users/jinwoo/Desktop/setting.png"));

        friendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Friends");
            }
        });

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Chats");
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Settings");
            }
        });

        // 버튼들을 패널에 추가
        buttonPanel.add(friendsButton);
//        buttonPanel.add(Box.createVerticalGlue()); // 버튼 사이에 공간 추가
        buttonPanel.add(chatButton);
        buttonPanel.add(Box.createVerticalGlue()); // 하단 버튼 전에 공간 추가
        buttonPanel.add(Box.createVerticalGlue()); // 추가 공간
        buttonPanel.add(settingsButton);

        // 버튼 패널을 왼쪽에 배치
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);

        // 기본 화면 설정
        cardLayout.show(cardPanel, "Friends");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainApplicationFrame().setVisible(true);
            }
        });
    }
}
