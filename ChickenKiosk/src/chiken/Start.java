package chiken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Start {

    private static int totalPrice = 0;
    private static JLabel totalLabel;
    private static DefaultListModel<String> chickenListModel = new DefaultListModel<>();
    private static Map<String, Integer> chickenCountMap = new HashMap<>();
    private static JFrame frame, menuFrame, totalFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // JFrame 설정
            frame = new JFrame("처갓집 죽여보자고 ㅋ");
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
                menuFrame = new JFrame("메뉴 화면");
                menuFrame.setSize(900, 900);
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

                // 합계 표시용 프레임
                totalFrame = new JFrame("총 합계");
                totalFrame.setSize(300, 600);
                totalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                totalFrame.setLayout(new BorderLayout());

                totalLabel = new JLabel("총 가격: 0원");
                totalLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel chickenPanel = new JPanel();
                chickenPanel.setLayout(new BoxLayout(chickenPanel, BoxLayout.Y_AXIS));

                JScrollPane scrollPane = new JScrollPane(chickenPanel);

                JButton payButton = new JButton("결제하기");
                payButton.addActionListener(e4 -> {
                    // 모든 프레임 닫기
                    frame.dispose();
                    menuFrame.dispose();
                    totalFrame.dispose();

                    // 결제 성공 메시지 프레임
                    JFrame successFrame = new JFrame("결제 성공");
                    successFrame.setSize(300, 200);
                    successFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    successFrame.setLayout(new BorderLayout());
                    JLabel successLabel = new JLabel("결제 성공!", SwingConstants.CENTER);
                    successFrame.add(successLabel, BorderLayout.CENTER);

                    JButton successButton = new JButton("확인");
                    successButton.addActionListener(e5 -> {
                        successFrame.dispose();
                    });
                    successFrame.add(successButton, BorderLayout.SOUTH);

                    successFrame.setLocationRelativeTo(null);
                    successFrame.setVisible(true);
                });

                totalFrame.add(totalLabel, BorderLayout.NORTH);
                totalFrame.add(scrollPane, BorderLayout.CENTER);
                totalFrame.add(payButton, BorderLayout.SOUTH);

                // 메뉴 프레임과 합계 프레임 위치 설정
                menuFrame.setLocationRelativeTo(frame);
                Point menuLocation = menuFrame.getLocation();
                totalFrame.setLocation(menuLocation.x + menuFrame.getWidth() + 10, menuLocation.y);

                // 메뉴 화면을 화면 중앙에 표시
                menuFrame.setVisible(true);
                totalFrame.setVisible(true);

                // 초기 치킨 목록 업데이트
                updateChickenList(chickenPanel);
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
            "후라이드", "반반치킨", "양념치킨",
            "트러플 슈프림 양념치킨", "파인유자", "골드치즈",
            "슈프림 골드 양념치킨", "와락윙", "와락치킨"
        };
        int[] prices = {
            18000, 20000, 20000,
            21000, 22000, 20000,
            20000, 20000, 19000
        };

        JLabel chickenLabel = new JLabel(chickenTypes[index] + ": " + prices[index] + "원");
        priceFrame.add(chickenLabel);

        JButton addButton = new JButton("추가");
        addButton.addActionListener(e -> {
            String chickenName = chickenTypes[index];
            int price = prices[index];

            totalPrice += price;

            if (chickenCountMap.containsKey(chickenName)) {
                chickenCountMap.put(chickenName, chickenCountMap.get(chickenName) + 1);
            } else {
                chickenCountMap.put(chickenName, 1);
            }

            updateChickenList((JPanel) ((JScrollPane) totalFrame.getContentPane().getComponent(1)).getViewport().getView());
            updateTotalLabel();
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

    private static void updateChickenList(JPanel chickenPanel) {
        chickenPanel.removeAll();
        for (Map.Entry<String, Integer> entry : chickenCountMap.entrySet()) {
            String chickenName = entry.getKey();
            int count = entry.getValue();

            JLabel chickenLabel = new JLabel(count > 1 ? chickenName + " + " + count : chickenName);
            chickenPanel.add(chickenLabel);

            JButton cancelButton = new JButton("선택 취소");
            cancelButton.addActionListener(e -> {
                totalPrice -= getPriceByName(chickenName);
                if (chickenCountMap.get(chickenName) > 1) {
                    chickenCountMap.put(chickenName, chickenCountMap.get(chickenName) - 1);
                } else {
                    chickenCountMap.remove(chickenName);
                }
                updateChickenList(chickenPanel);
                updateTotalLabel();
            });
            chickenPanel.add(cancelButton);
        }
        chickenPanel.revalidate();
        chickenPanel.repaint();
    }

    private static void updateTotalLabel() {
        if (totalLabel != null) {
            totalLabel.setText("총 가격: " + totalPrice + "원");
        }
    }

    private static int getPriceByName(String name) {
        switch (name) {
            case "후라이드":
                return 18000;
            case "반반치킨":
                return 20000;
            case "양념치킨":
                return 20000;
            case "트러플 슈프림 양념치킨":
                return 21000;
            case "파인유자":
                return 22000;
            case "골드치즈":
                return 20000;
            case "슈프림 골드 양념치킨":
                return 20000;
            case "와락윙":
                return 20000;
            case "와락치킨":
                return 19000;
            default:
                return 0;
        }
    }
}