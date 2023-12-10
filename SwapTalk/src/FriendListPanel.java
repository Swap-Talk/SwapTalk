import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

public class FriendListPanel extends JPanel {
	private JList<Friend> friendList;
	private DefaultListModel<Friend> listModel;

	public FriendListPanel() {
		setLayout(new BorderLayout());
		listModel = new DefaultListModel<>();

		// 상단 헤더 패널 구성
		JPanel headerPanel = new JPanel(new BorderLayout());
		JLabel lblTitle = new JLabel("친구");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18)); // Bold체로 설정
		headerPanel.add(lblTitle, BorderLayout.WEST);

		// 상단 헤더 버튼 추가
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnSearch = new JButton(new ImageIcon("/Users/jinwoo/Desktop/Search_light.png"));
		JButton btnAddFriend = new JButton(new ImageIcon("/Users/jinwoo/Desktop/User_add_alt_light.png"));
		buttonPanel.add(btnSearch);
		buttonPanel.add(btnAddFriend);
		headerPanel.add(buttonPanel, BorderLayout.EAST);

		// 상단 헤더를 메인 패널에 추가
		add(headerPanel, BorderLayout.NORTH);

		// 나의 프로필을 리스트 모델에 추가
//		listModel.addElement(new Friend("/Users/jinwoo/Desktop/cat 1.png", "박진우"));

		// 친구 목록 추가
		addDummyFriends();

		friendList = new JList<>(listModel);
		friendList.setCellRenderer(new FriendCellRenderer()); // 커스텀 렌더러 설정

		JScrollPane scrollPane = new JScrollPane(friendList);
		add(scrollPane, BorderLayout.CENTER);
	}

	private void addDummyFriends() {
		// 더미 데이터로 10명의 친구를 추가합니다.
		// 실제 이미지 경로와 이름을 사용해야 합니다.
		String basePath = "/Users/jinwoo/Desktop/"; // 예를 들어 프로필 이미지가 있는 기본 경로
		listModel.addElement(new Friend(basePath + "cat 7.png", "김철수"));
		listModel.addElement(new Friend(basePath + "cat 6.png", "이영희"));
		listModel.addElement(new Friend(basePath + "cat 5.png", "유재석"));
		listModel.addElement(new Friend(basePath + "cat 4.png", "박명수"));
		listModel.addElement(new Friend(basePath + "cat 11.png", "정준하"));
		listModel.addElement(new Friend(basePath + "cat 10.png", "노홍철"));
		listModel.addElement(new Friend(basePath + "cat 13.png", "송중기"));
		listModel.addElement(new Friend(basePath + "cat 12.png", "홍길동"));
		listModel.addElement(new Friend(basePath + "cat 14.png", "김미영"));
		// ... 나머지 친구들을 위한 코드를 추가합니다.
	}

	// JList에서 친구 정보를 표시하기 위한 커스텀 셀 렌더러
	private static class FriendCellRenderer extends JPanel implements ListCellRenderer<Friend> {
		private JLabel lblName = new JLabel();
		private JLabel lblIcon = new JLabel();

		public FriendCellRenderer() {
			setLayout(new BorderLayout(5, 5));
			lblName.setFont(new Font("Arial", Font.PLAIN, 15));
			add(lblIcon, BorderLayout.WEST);
			add(lblName, BorderLayout.CENTER);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Friend> list, Friend friend, int index,
				boolean isSelected, boolean cellHasFocus) {
			lblName.setText(friend.getName());
			ImageIcon icon = new ImageIcon(new ImageIcon(friend.getProfileImagePath()).getImage().getScaledInstance(50,
					50, Image.SCALE_SMOOTH));
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
