import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

public class LoginDialog extends JDialog {
    // 로그인 정보를 입력받을 텍스트 필드
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;

    public LoginDialog(Frame parent) {
        super(parent, "로그인", true);
        //
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        // 사용자 이름 입력 필드
        JLabel lbUsername = new JLabel("아이디: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        txtUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(txtUsername, cs);

        // 비밀번호 입력 필드
        JLabel lbPassword = new JLabel("비밀번호: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        txtPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(txtPassword, cs);

     // 로그인 버튼
        btnLogin = new JButton("로그인");
        cs.gridx = 0; // 수정된 부분: 버튼의 위치 설정
        cs.gridy = 2; // 수정된 부분: 버튼의 위치 설정
        cs.gridwidth = 1; // 수정된 부분: 버튼이 차지할 그리드의 너비 설정
        panel.add(btnLogin, cs); // 수정된 부분: 버튼을 패널에 추가

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (authenticate(getUsername(), getPassword())) {
                    // 인증 성공
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "안녕하세요 " + getUsername() + "님, 로그인에 성공하셨습니다.",
                            "로그인",
                            JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                    dispose(); // 로그인 창을 닫습니다.

                    // 메인 화면을 여는 코드를 여기에 추가합니다.
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            MainApplicationFrame mainFrame = new MainApplicationFrame(); // 메인 화면 인스턴스 생성
                            mainFrame.setVisible(true); // 메인 화면을 보이게 설정
                        }
                    });
                } else {
                    // 인증 실패
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "아이디 또는 비밀번호가 잘못되었습니다.",
                            "로그인",
                            JOptionPane.ERROR_MESSAGE);
                    txtPassword.setText("");
                    succeeded = false;
                }
            }
        });


        panel.add(btnLogin, cs);

     // 취소 버튼
        btnCancel = new JButton("취소");
        cs.gridx = 1; // 수정된 부분: 버튼의 위치 설정
        cs.gridy = 2; // 수정된 부분: 버튼의 위치 설정
        cs.gridwidth = 1; // 수정된 부분: 버튼이 차지할 그리드의 너비 설정
        panel.add(btnCancel, cs); // 수정된 부분: 버튼을 패널에 추가
        
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnCancel, cs);

        getContentPane().add(panel, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    // 이 메소드는 사용자 이름과 비밀번호를 인증하는 실제 로직을 구현해야 합니다.
    // 예시를 위해 단순히 true를 반환합니다.
    private boolean authenticate(String username, String password) {
        // 실제 로그인 인증 로직을 구현해야 합니다.
        // 예: 서버에 요청을 보내서 확인합니다.
        return true;
    }
}
