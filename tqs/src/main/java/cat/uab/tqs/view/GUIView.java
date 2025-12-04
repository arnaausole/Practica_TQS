package cat.uab.tqs.view;

import javax.swing.*;
import java.awt.*;
import cat.uab.tqs.controller.GameController;
import cat.uab.tqs.model.*;

public class GUIView implements GameView {

    private JFrame frame;
    private JPanel playerCardsPanel;
    private JPanel dealerCardsPanel;
    private JLabel playerScore;
    private JLabel dealerScore;
    private JLabel statusLabel;
    private JButton startButton;
    private JButton hitButton;
    private JButton standButton;

    private GameController controller;

    private boolean dealerHidden = false;

    public GUIView() {
        initGUI();
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void initGUI() {

        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        Color tableGreen = new Color(0, 102, 0);
        Color darkGreen = new Color(0, 70, 0);
        Color gold = new Color(212, 175, 55);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(darkGreen);
        statusLabel = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        statusLabel.setForeground(gold);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(statusLabel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(tableGreen);
        tablePanel.setLayout(new GridLayout(2, 1, 0, 20));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // DEALER
        JPanel dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.setOpaque(false);

        JPanel dealerHeader = new JPanel(new BorderLayout());
        dealerHeader.setOpaque(false);
        JLabel dealerLabel = new JLabel("DEALER");
        dealerLabel.setForeground(Color.WHITE);
        dealerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        dealerScore = new JLabel("Score: 0", SwingConstants.RIGHT);
        dealerScore.setForeground(Color.WHITE);
        dealerScore.setFont(new Font("SansSerif", Font.PLAIN, 16));
        dealerHeader.add(dealerLabel, BorderLayout.WEST);
        dealerHeader.add(dealerScore, BorderLayout.EAST);

        dealerCardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        dealerCardsPanel.setOpaque(false);

        dealerPanel.add(dealerHeader, BorderLayout.NORTH);
        dealerPanel.add(dealerCardsPanel, BorderLayout.CENTER);

        // PLAYER
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setOpaque(false);

        JPanel playerHeader = new JPanel(new BorderLayout());
        playerHeader.setOpaque(false);
        JLabel playerLabel = new JLabel("PLAYER");
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        playerScore = new JLabel("Score: 0", SwingConstants.RIGHT);
        playerScore.setForeground(Color.WHITE);
        playerScore.setFont(new Font("SansSerif", Font.PLAIN, 16));
        playerHeader.add(playerLabel, BorderLayout.WEST);
        playerHeader.add(playerScore, BorderLayout.EAST);

        playerCardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        playerCardsPanel.setOpaque(false);

        playerPanel.add(playerHeader, BorderLayout.NORTH);
        playerPanel.add(playerCardsPanel, BorderLayout.CENTER);

        tablePanel.add(dealerPanel);
        tablePanel.add(playerPanel);

        frame.add(tablePanel, BorderLayout.CENTER);

        // BOTONS
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(darkGreen);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        startButton = new JButton("New Game");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

        Dimension btnSize = new Dimension(120, 40);
        Font btnFont = new Font("SansSerif", Font.BOLD, 14);

        JButton[] buttons = {startButton, hitButton, standButton};
        for (JButton b : buttons) {
            b.setPreferredSize(btnSize);
            b.setFont(btnFont);
            b.setFocusPainted(false);
            b.setBackground(gold);
            b.setForeground(Color.BLACK);
            b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }

        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        buttonsPanel.add(startButton);
        buttonsPanel.add(hitButton);
        buttonsPanel.add(standButton);

        frame.add(buttonsPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            controller.startGame();
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
        });

        hitButton.addActionListener(e -> controller.playerHit());

        standButton.addActionListener(e -> {
            controller.playerStand();
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        });

        frame.setVisible(true);
    }

    private JPanel createCardComponent(Card c) {
        JPanel cardPanel = new JPanel();
        cardPanel.setPreferredSize(new Dimension(60, 90));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        cardPanel.setLayout(new BorderLayout());

        String suit = c.getSuit();
        String symbol = "?";
        if ("Hearts".equals(suit)) symbol = "♥";
        else if ("Diamonds".equals(suit)) symbol = "♦";
        else if ("Clubs".equals(suit)) symbol = "♣";
        else if ("Spades".equals(suit)) symbol = "♠";

        boolean isRed = "Hearts".equals(suit) || "Diamonds".equals(suit);
        Color textColor = isRed ? Color.RED : Color.BLACK;

        JLabel top = new JLabel(c.getRank(), SwingConstants.LEFT);
        top.setFont(new Font("SansSerif", Font.BOLD, 14));
        top.setForeground(textColor);
        top.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 0));

        JLabel center = new JLabel(symbol, SwingConstants.CENTER);
        center.setFont(new Font("SansSerif", Font.PLAIN, 28));
        center.setForeground(textColor);

        JLabel bottom = new JLabel(c.getRank(), SwingConstants.RIGHT);
        bottom.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottom.setForeground(textColor);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 4));

        cardPanel.add(top, BorderLayout.NORTH);
        cardPanel.add(center, BorderLayout.CENTER);
        cardPanel.add(bottom, BorderLayout.SOUTH);

        return cardPanel;
    }

    private JPanel createHiddenCardComponent() {
        JPanel cardPanel = new JPanel();
        cardPanel.setPreferredSize(new Dimension(60, 90));
        cardPanel.setBackground(new Color(0, 0, 128)); // blau fosc tipus dors
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        cardPanel.setLayout(new BorderLayout());

        JLabel center = new JLabel("★", SwingConstants.CENTER);
        center.setFont(new Font("SansSerif", Font.BOLD, 26));
        center.setForeground(Color.WHITE);

        cardPanel.add(center, BorderLayout.CENTER);
        return cardPanel;
    }

    private void refreshCards() {
        if (controller == null) {
            return;
        }

        // PLAYER
        playerCardsPanel.removeAll();
        for (Card c : controller.getPlayer().getHand().getCards()) {
            playerCardsPanel.add(createCardComponent(c));
        }

        // DEALER
        dealerCardsPanel.removeAll();
        java.util.List<Card> dealerHand = controller.getDealer().getHand().getCards();

        if (dealerHidden && !dealerHand.isEmpty()) {
            // 1a carta visible
            dealerCardsPanel.add(createCardComponent(dealerHand.get(0)));
            // resta ocultes
            for (int i = 1; i < dealerHand.size(); i++) {
                dealerCardsPanel.add(createHiddenCardComponent());
            }
        } else {
            for (Card c : dealerHand) {
                dealerCardsPanel.add(createCardComponent(c));
            }
        }

        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();
        dealerCardsPanel.revalidate();
        dealerCardsPanel.repaint();
    }

    @Override
    public void showMessage(String msg) {
        statusLabel.setText(msg);
    }

    @Override
    public void showCard(Player player, Card c) {
        refreshCards();
    }

    @Override
    public void updateScores(int playerValue, int dealerValue) {
        playerScore.setText("Score: " + playerValue);

        if (dealerHidden) {
            dealerScore.setText("Score: ?");
        } else {
            dealerScore.setText("Score: " + dealerValue);
        }

        refreshCards();
    }

    @Override
    public void setDealerHidden(boolean hidden) {
        this.dealerHidden = hidden;
        refreshCards();
    }
}
