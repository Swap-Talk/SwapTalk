import java.awt.*;
import javax.swing.*;

public class ChatListPanel extends JPanel {
    private JList<Chat> chatList;
    private DefaultListModel<Chat> listModel;

    public ChatListPanel() {
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();

        // 상단 헤더 패널 구성
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("채팅");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18)); // Bold체로 설정
        headerPanel.add(lblTitle, BorderLayout.WEST);

        // 상단 헤더 버튼 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSearch = new JButton(new ImageIcon("/Users/jinwoo/Desktop/Search_light.png"));
        JButton btnAddChat = new JButton(new ImageIcon("/Users/jinwoo/Desktop/Chat_plus_light.png"));
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnAddChat);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // 상단 헤더를 메인 패널에 추가
        add(headerPanel, BorderLayout.NORTH);

        // 채팅 목록 추가
        addDummyChats();

        chatList = new JList<>(listModel);
        chatList.setCellRenderer(new ChatCellRenderer()); // 커스텀 렌더러 설정

        JScrollPane scrollPane = new JScrollPane(chatList);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addDummyChats() {
        // 더미 데이터로 채팅을 추가합니다.
        // 실제 이미지 경로와 이름, 마지막 메시지를 사용해야 합니다.
        listModel.addElement(new Chat("/Users/jinwoo/Desktop/cat 7.png", "송중기", "자니..?"));
        listModel.addElement(new Chat("/Users/jinwoo/Desktop/cat 6.png", "이광수", "재석이 형 !!!"));
        // ... 나머지 채팅들을 위한 코드를 추가합니다.
    }

    // 채팅 목록을 나타내는 클래스
    private static class Chat {
        String profileImagePath;
        String name;
        String lastMessage;

        public Chat(String profileImagePath, String name, String lastMessage) {
            this.profileImagePath = profileImagePath;
            this.name = name;
            this.lastMessage = lastMessage;
        }

        // getters here
    }

    // JList에서 채팅 정보를 표시하기 위한 커스텀 셀 렌더러
    private static class ChatCellRenderer extends JPanel implements ListCellRenderer<Chat> {
        private JLabel lblName = new JLabel();
        private JLabel lblLastMessage = new JLabel();
        private JLabel lblIcon = new JLabel();

        public ChatCellRenderer() {
            setLayout(new BorderLayout(5, 5));
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.add(lblName, BorderLayout.NORTH);
            textPanel.add(lblLastMessage, BorderLayout.SOUTH);
            lblName.setFont(new Font("Arial", Font.BOLD, 14));
            lblLastMessage.setFont(new Font("Arial", Font.PLAIN, 12));
            add(lblIcon, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Chat> list, Chat chat, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            lblName.setText(chat.name);
            lblLastMessage.setText(chat.lastMessage);
            ImageIcon icon = new ImageIcon(new ImageIcon(chat.profileImagePath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            lblIcon.setIcon(icon);
            lblIcon.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }
}
