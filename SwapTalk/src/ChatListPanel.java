import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class ChatListPanel extends JPanel {
	private JList<Chat> chatList;
	private DefaultListModel<Chat> listModel;
	private DatagramSocket socket;
	private static List<JTextArea> allChatAreas = new ArrayList<>();

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

		// 채팅 추가 버튼 액션 리스너
		btnAddChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openChatWindow(5000, 6000); // 채팅 창을 여는 메소드 호출
				openChatWindow(6000, 5000);
			}
		});

		// 상단 헤더를 메인 패널에 추가
		add(headerPanel, BorderLayout.NORTH);

		// 채팅 목록 추가
		addDummyChats();

		chatList = new JList<>(listModel);
		chatList.setCellRenderer(new ChatCellRenderer()); // 커스텀 렌더러 설정

		JScrollPane scrollPane = new JScrollPane(chatList);
		add(scrollPane, BorderLayout.CENTER);
	}

	// 채팅 창 배경색을 변경하는 메소드
	public static void updateChatBackgroundColor(Color color) {
		for (JTextArea chatArea : allChatAreas) {
			chatArea.setBackground(color);
		}
	}

	// 채팅 창을 여는 메소드
	void openChatWindow(int myPort, int targetPort) {
		JFrame chatWindow;
		// 채팅 창 JFrame을 생성하고 설정
		if (myPort == 5000) {
			chatWindow = new JFrame("Chat A");
		} else {
			chatWindow = new JFrame("Chat B");
		}
		chatWindow.setSize(300, 400);
		chatWindow.setLayout(new BorderLayout());
		chatWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 메시지를 입력받을 텍스트 필드
		JTextField messageInput = new JTextField();
		chatWindow.add(messageInput, BorderLayout.PAGE_END);

		JTextArea chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(SettingsPanel.selectedChatBackgroundColor); // 현재 설정된 배경색 사용
		allChatAreas.add(chatArea); // 새로운 JTextArea를 리스트에 추가

		JScrollPane chatScrollPane = new JScrollPane(chatArea);
		chatWindow.add(chatScrollPane, BorderLayout.CENTER);

		// 각 채팅 창마다 독립적인 DatagramSocket 인스턴스 생성
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(myPort);
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		// 메시지 입력 시 액션 리스너
		messageInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String message = messageInput.getText();
					if (!message.isEmpty()) {
						chatArea.append("나: " + message + "\n");
						messageInput.setText("");

						byte[] buffer = message.getBytes();
						InetAddress address = InetAddress.getByName("127.0.0.1");
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, targetPort);
						socket.send(packet);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// 수신 스레드 시작
		Thread receiveThread = new Thread(() -> {
			try {
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				while (true) {
					socket.receive(packet);
					String receivedMessage = new String(packet.getData(), 0, packet.getLength());
					SwingUtilities.invokeLater(() -> chatArea.append("상대방: " + receivedMessage + "\n"));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		receiveThread.start();

		// 채팅 창을 화면에 표시
		chatWindow.setVisible(true);
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
			ImageIcon icon = new ImageIcon(
					new ImageIcon(chat.profileImagePath).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
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