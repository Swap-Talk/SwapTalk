import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SettingsPanel extends JPanel {
	public static Color selectedChatBackgroundColor = Color.WHITE;
	private CardLayout cardLayout;
	private JPanel cardPanel;

	public SettingsPanel() {
		setLayout(new BorderLayout());
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		JPanel chatSettingsPanel = createChatSettingsPanel();
		JPanel profileSettingsPanel = createProfileSettingsPanel();

		cardPanel.add(chatSettingsPanel, "채팅");
		cardPanel.add(profileSettingsPanel, "프로필");

		// 상단 탭 버튼 패널
		JPanel tabButtonPanel = new JPanel(new GridLayout(1, 2));
		JButton chatButton = new JButton("채팅");
		JButton profileButton = new JButton("프로필");
		tabButtonPanel.add(chatButton);
		tabButtonPanel.add(profileButton);

		// 상단 버튼에 액션 리스너 추가
		chatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "채팅");
			}
		});
		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "프로필");
			}
		});

		add(tabButtonPanel, BorderLayout.NORTH);
		add(cardPanel, BorderLayout.CENTER);
	}

	private JPanel createChatSettingsPanel() {
		// 첫 번째 사진과 같은 글꼴 및 채팅창 배경색 설정 패널
		JPanel chatSettingsPanel = new JPanel();
		chatSettingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		chatSettingsPanel.setLayout(new BoxLayout(chatSettingsPanel, BoxLayout.Y_AXIS));

		// 글꼴 설정 드롭다운
		chatSettingsPanel.add(new JLabel("글꼴"));
		JComboBox<String> fontComboBox = new JComboBox<>(new String[] { "Pretendard", "Arial", "Verdana" });
		chatSettingsPanel.add(fontComboBox);

		// 배경색 설정 영역
		chatSettingsPanel.add(new JLabel("배경"));
		JPanel colorPanel = new JPanel(new GridLayout(2, 3, 10, 10));

		// 색상 선택 옵션 추가
		Color[] colors = { Color.RED, Color.PINK, Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };
		for (Color color : colors) {
			JLabel colorLabel = new JLabel(new ColorIcon(color));
			colorLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// 이벤트 처리: 선택된 색상을 ChatListPanel에 알림
					ChatListPanel.updateChatBackgroundColor(color);
				}
			});
			colorPanel.add(colorLabel);
		}

		chatSettingsPanel.add(colorPanel);

		return chatSettingsPanel;
	}

	private JPanel createProfileSettingsPanel() {
		// 두 번째 사진과 같은 프로필 설정 패널
		JPanel profileSettingsPanel = new JPanel();
		profileSettingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		profileSettingsPanel.setLayout(new BoxLayout(profileSettingsPanel, BoxLayout.Y_AXIS));

		// 프로필 사진
		profileSettingsPanel.add(new JLabel("프로필 패널"));
		ImageIcon profileIcon = new ImageIcon("/Users/jinwoo/Desktop/cat 1.png"); // 프로필 사진 경로
		JLabel profileLabel = new JLabel(
				new ImageIcon(profileIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		profileSettingsPanel.add(profileLabel);

		// 이름 설정
		JTextField nameTextField = new JTextField("박진우");
		profileSettingsPanel.add(nameTextField);

		// 변경 사항 저장 버튼
		JButton saveButton = new JButton("저장");
		profileSettingsPanel.add(saveButton);

		return profileSettingsPanel;
	}

	private static class ColorIcon implements Icon {
		private static final int ICON_SIZE = 50;
		private Color color;

		public ColorIcon(Color color) {
			this.color = color;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(color);
			g.fillRect(x, y, ICON_SIZE, ICON_SIZE);
		}

		@Override
		public int getIconWidth() {
			return ICON_SIZE;
		}

		@Override
		public int getIconHeight() {
			return ICON_SIZE;
		}
	}
}
