package Client;

/**
 * Created by Brandon on 24/05/2017.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;


//////////////////////////////////////
////// INTERFACE POUR LE CLIENT //////
//////////////////////////////////////

//A LANCER AU LIEU DU MAIN DANS LE CLIENT
public class ClientView extends JFrame implements ActionListener {
    private static final long serialVersionUID = -1374851023131011832L;

    private JPanel contentPane;

    private JTextArea txtHeaderArea;
    private JTextArea txtPageArea;

    private JScrollPane scrollHeaderPane;
    private JScrollPane scrollPagePane;

    private JProgressBar progressBar;

    private JLabel statusLabel;

    private JTextField txtAdresse;
    private JTextField txtFile;

    private JButton btnGet;
    private JPanel panelPage;
    JLabel lblImage;



    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientView frame = new ClientView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ClientView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        statusLabel = new JLabel("Statut");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        JPanel statusPanel = new JPanel();
        statusPanel.setBounds(2, 496, 573, 20);
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setLayout(new GridLayout(1, 2));
        statusPanel.add(statusLabel);
        statusPanel.add(progressBar);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.add(statusPanel);
        setContentPane(contentPane);

        JLabel lblAdresse = new JLabel("Adresse");
        lblAdresse.setBounds(6, 16, 61, 16);
        contentPane.add(lblAdresse);

        txtAdresse = new JTextField();
        txtAdresse.setBounds(79, 10, 183, 28);
        contentPane.add(txtAdresse);
        txtAdresse.setColumns(10);

        txtFile = new JTextField();
        txtFile.setColumns(10);
        txtFile.setBounds(279, 10, 183, 28);
        contentPane.add(txtFile);

        JLabel label = new JLabel(" /");
        label.setBounds(264, 17, 15, 16);
        contentPane.add(label);

        btnGet = new JButton("GET");
        btnGet.setBounds(474, 10, 98, 30);
        btnGet.addActionListener(this);
        contentPane.add(btnGet);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(5, 44, 573, 451);
        contentPane.add(tabbedPane);

        JPanel panelCode = new JPanel();
        tabbedPane.addTab("Code", null, panelCode, null);
        panelCode.setLayout(null);

        txtHeaderArea = new JTextArea();
        txtHeaderArea.setEditable(false);
        scrollHeaderPane = new JScrollPane(txtHeaderArea);
        scrollHeaderPane.setBounds(6, 6, 540, 140);
        panelCode.add(scrollHeaderPane);

        txtPageArea = new JTextArea();
        txtPageArea.setEditable(false);
        scrollPagePane = new JScrollPane(txtPageArea);
        scrollPagePane.setBounds(6, 150, 540, 252);
        panelCode.add(scrollPagePane);

        panelPage = new JPanel();
        tabbedPane.addTab("Image", null, panelPage, null);
        panelPage.setLayout(null);

        lblImage = new JLabel("");
        lblImage.setBounds(6, 6, 540, 393);
        panelPage.add(lblImage);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnGet) {
            txtPageArea.setText("");
            txtHeaderArea.setText("");
            statusLabel.setText("Reception");
            lblImage.setIcon(null);

            String commande ="GET /";
            commande+=txtAdresse.getText();
            commande+="/";
            commande+=txtFile.getText();
            commande+=" HTTP/1.1";

            Client client = new Client(commande, txtAdresse.getText(), txtFile.getText());
            while(client.fileContent == "") {}
            if(client.fileType.contains("jpg") || client.fileType.contains("png")) {
                // ImageIcon imageIcon = new ImageIcon(new ImageIcon(client.fileType).getImage().getScaledInstance(540,393, Image.SCALE_SMOOTH));
                ImageIcon imageIcon = new ImageIcon(client.fileType);
                JDialog dialog = new JDialog();
                dialog.setTitle(client.fileName);
                JLabel label = new JLabel(imageIcon);
                dialog.add(label);
                dialog.pack();
                dialog.setVisible(true);
                lblImage.setIcon(imageIcon);
                txtPageArea.append("Image reçue, \n     => Voir onglet Image");
            }
            else {
                txtPageArea.append(client.fileContent);
                txtHeaderArea.append(client.header);
            }
            statusLabel.setText("Reçu");

        }
    }
}
