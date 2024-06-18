package chiken;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Start {
    
    private static int totalPrice = 0;
    private static JLabel priceLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // JFrame 설정
            JFrame frame = new JFrame("처갓집 죽여보자고 ㅋ");
            frame.setSize(650, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null); // 레이아웃 매니저 해제

            // 이미지 표시용 JLabel 설정
            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon(Start.class.getResource("m_menu.png")); // 이미지 경로 설정
            imageLabel.setIcon(imageIcon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // JLabel을 가운데 정렬
            imageLabel.setBounds(0, 0, 650, 400); // 이미지 위치와 크기 설정
            frame.add(imageLabel);

            // 버튼 생성 및 설정
            JButton button = new JButton("메뉴로 이동");
            button.setBounds(430, 280, 120, 30); // 버튼 위치와 크기 설정
            button.setOpaque(false); // 배경을 투명하게 설정
            button.setContentAreaFilled(false); // 내용 영역을 투명하게 설정
            button.setEnabled(true); // 버튼 활성화
            frame.add(button);

            // 버튼 클릭 이벤트 처리
            button.addActionListener(e -> {
                // 메뉴 화면 프레임
                JFrame menuFrame = new JFrame("메뉴 화면");
                menuFrame.setSize(600, 600);
                menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);               
                menuFrame.setLayout(new GridLayout(3, 3)); // 3x3 그리드 레이아웃 설정

                // 이미지 아이콘들
                ImageIcon[] menuIcons = new ImageIcon[9];
                for (int i = 0; i < 9; i++) {
                    menuIcons[i] = new ImageIcon(Start.class.getResource("/image" + (i + 1) + ".png"));
                }

                // 이미지를 담을 JButton 배열
                JButton[] imageButtons = new JButton[9];
                for (int i = 0; i < 9; i++) {
                    imageButtons[i] = new JButton(menuIcons[i]);
                    imageButtons[i].setPreferredSize(new Dimension(100, 100)); // 버튼 크기 설정
                    menuFrame.add(imageButtons[i]);
                }

                // 각 이미지 버튼에 ActionListener 추가
                for (int i = 0; i < 9; i++) {
                    int index = i; // ActionListener 내부에서 사용하기 위한 인덱스
                    imageButtons[i].addActionListener(e2 -> {
                        // 각 이미지 버튼 클릭 시 동작할 코드 추가
                        showPriceFrame(index);
                    });
                }

                // 메뉴 화면을 화면 중앙에 표시2
                menuFrame.setLocationRelativeTo(frame);
                menuFrame.setVisible(true);
            });

            // JFrame 표시
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void showPriceFrame(int index) {
        JFrame priceFrame = new JFrame("가격 정보");
        priceFrame.setSize(300, 200);
        priceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        priceFrame.setLayout(new FlowLayout());

        // 치킨 종류와 가격 정보
        String[] chickenTypes = {
            "후라이드", "양념치킨", "간장치킨", 
            "마늘치킨", "파닭", "치즈치킨", 
            "고추치킨", "바베큐치킨", "허니갈릭치킨"
        };
        int[] prices = {
            18000, 20000, 20000,
            22000, 23000, 25000,
            24000, 26000, 27000
        };

        JLabel chickenLabel = new JLabel(chickenTypes[index] + ": " + prices[index] + "원");
        priceFrame.add(chickenLabel);

        JButton addButton = new JButton("추가");
        addButton.addActionListener(e -> {
            totalPrice += prices[index];
            updatePriceLabel();
            priceFrame.dispose();
        });
        priceFrame.add(addButton);

        JButton cancelButton = new JButton("취소");
        cancelButton.addActionListener(e -> {
            priceFrame.dispose();
        });
        priceFrame.add(cancelButton);

        priceFrame.setLocationRelativeTo(null);
        priceFrame.setVisible(true);
    }

    private static void updatePriceLabel() {
        if (priceLabel != null) {
            priceLabel.setText("총 가격: " + totalPrice + "원");
        }
    }
}