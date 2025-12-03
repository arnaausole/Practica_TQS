package cat.uab.tqs.view;

import javax.swing.*;
import java.awt.*;
import cat.uab.tqs.controller.GameController;
import cat.uab.tqs.model.*;

public class GUIView implements GameView {

    private JFrame frame;
    private JTextArea playerCards;
    private JTextArea dealerCards;
    private JLabel playerScore;
    private JLabel dealerScore;
    private JLabel statusLabel;
    private JButton startButton;
    private JButton hitButton;
    private JButton standButton;

    private GameController controller;

    public GUIView() {
        initGUI();
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void initGUI() {

        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(statusLabel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player"));
        playerScore = new JLabel("Score: 0");
        playerCards = new JTextArea();
        playerCards.setEditable(false);
        playerCards.setFont(new Font("Monospaced", Font.PLAIN, 14));
        playerPanel.add(playerScore, BorderLayout.NORTH);
        playerPanel.add(new JScrollPane(playerCards), BorderLayout.CENTER);

        JPanel dealerPanel = new JPanel();
        dealerPanel.setLayout(new BorderLayout());
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer"));
        dealerScore = new JLabel("Score: 0");
        dealerCards = new JTextArea();
        dealerCards.setEditable(false);
        dealerCards.setFont(new Font("Monospaced", Font.PLAIN, 14));
        dealerPanel.add(dealerScore, BorderLayout.NORTH);
        dealerPanel.add(new JScrollPane(dealerCards), BorderLayout.CENTER);

        centerPanel.add(playerPanel);
        centerPanel.add(dealerPanel);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        startButton = new JButton("Start");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

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

    @Override
    public void showMessage(String msg) {
        statusLabel.setText(msg);
    }

    @Override
    public void showCard(Player player, Card c) {
        playerCards.append(c.getRank() + " of " + c.getSuit() + "\n");
    }

    @Override
    public void updateScores(int playerValue, int dealerValue) {

        playerScore.setText("Score: " + playerValue);
        dealerScore.setText("Score: " + dealerValue);

        playerCards.setText("");
        for (Card c : controller.getPlayer().getHand().getCards()) {
            playerCards.append(c.getRank() + " of " + c.getSuit() + "\n");
        }

        dealerCards.setText("");
        for (Card c : controller.getDealer().getHand().getCards()) {
            dealerCards.append(c.getRank() + " of " + c.getSuit() + "\n");
        }
    }
}
