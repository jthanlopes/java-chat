package Telas;

import Uteis.MD5;
import BD.ConexaoBD;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author jonsanto1
 */
public class TelaLogin extends javax.swing.JFrame {

    /**
     * Creates new form TelaCadastro
     */
    
    TelaCadastro cadastro = new TelaCadastro();
    public static String nomeUsuario;
    
    public TelaLogin() {
        initComponents();
        this.setTitle("Tela de Login");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        jtfEmail.grabFocus();
        jlAlerta.setVisible(false);
        this.setResizable(false);
        getRootPane().setDefaultButton(jbLogar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfEmail = new javax.swing.JTextField();
        jbLogar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlAlerta = new javax.swing.JLabel();
        jpfSenha = new javax.swing.JPasswordField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jbLogar.setText("Logar");
        jbLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLogarActionPerformed(evt);
            }
        });

        jLabel1.setText("E-mail");

        jLabel2.setText("Senha");

        jlAlerta.setForeground(new java.awt.Color(255, 0, 0));
        jlAlerta.setText("Alertas");

        jMenu1.setText("Opções");

        jMenuItem1.setText("Novo Cadastro");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Sair");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jbLogar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlAlerta))
                    .addComponent(jtfEmail)
                    .addComponent(jpfSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbLogar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlAlerta))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void limpaCampos() {
        jtfEmail.setText("");
        jtfEmail.grabFocus();
        jpfSenha.setText("");
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        cadastro.setVisible(true);        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jbLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLogarActionPerformed
        if (!"".equals(jtfEmail.getText()) && jpfSenha.getPassword().length != 0) {
            ConexaoBD conexao = new ConexaoBD();        
            TelaChat chat = new TelaChat();
            MD5 md5 = new MD5();
            String senha = new String(jpfSenha.getPassword());
            try {
                if (conexao.login(jtfEmail.getText(), md5.gerarMD5(senha))) {                                  
                    this.setVisible(false);
                    cadastro.setVisible(false);
                    chat.teste();
                    chat.setVisible(true);
                } else {
                    limpaCampos();
                    jlAlerta.setText("Usuário ou senha inválidos.");
                    jlAlerta.setVisible(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            jlAlerta.setText("Preencha todos os campos.");
            jlAlerta.setVisible(true);
        }
    }//GEN-LAST:event_jbLogarActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JButton jbLogar;
    private javax.swing.JLabel jlAlerta;
    private javax.swing.JPasswordField jpfSenha;
    private javax.swing.JTextField jtfEmail;
    // End of variables declaration//GEN-END:variables
}
