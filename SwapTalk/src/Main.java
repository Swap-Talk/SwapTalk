import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame parentFrame = new JFrame();
        LoginDialog loginDlg = new LoginDialog(parentFrame);
        loginDlg.setVisible(true);
        // 로그인 성공 후 메인 화면을 표시
        if (loginDlg.isSucceeded()) {
            // 메인 화면 클래스의 인스턴스를 생성하고 표시
            // 예: new MainFrame().setVisible(true);
        }
    }
}