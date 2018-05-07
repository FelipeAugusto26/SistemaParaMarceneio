/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infinitysolucoes.tela;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;


/**
 *
 * @author Usuario
 */
public class TelaLogin extends javax.swing.JFrame {

    // CARREGANDO VARIAVEL CONEXAO
    boolean status = false;
    Connection conexao;

    // MÉTODO QUE FECHA A TELA LOGIN
    public void fechar() {

        TelaPrincipal Tprincipal = new TelaPrincipal();
        Tprincipal.setVisible(true);
        this.dispose();
        

    }

    // MÉTODO CARREGAR PROGRESSO BAR
    private void carregar() {
        // CARREGANDO O PROGRESSO BAR
        btn_sair_login.setVisible(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean controle = true;
                    int i = 0;
                    while(controle == true){
                        i += 2;
                        jProgressBar_inicial.setValue(i);
                        Thread.sleep(100);
                        if (i == 30) {
                            lbl_carregamento_inicial.setForeground(Color.blue);
                            lbl_carregamento_inicial.setText("Carregando Banco de Dados...");
                        } else if (i == 70) {
                            lbl_carregamento_inicial.setForeground(Color.blue);
                            lbl_carregamento_inicial.setText("Iniciando Sistema...");
                            
                        }else if( jProgressBar_inicial.getValue() == 100){
                          
                            fechar();
                            break;
                            
                           
                            
                            
                        }
                      
                    } 

                } catch (Exception e) {
                }

            }
        }).start();

        /**
         * new Thread(new Runnable() {
         *
         * @Override public void run() { try {
         *
         * for (int i = 0; i <= 100;) { i = i + 5; Thread.sleep(50);
         * jProgressBar_inicial.setValue(i);
         * jProgressBar_inicial.setStringPainted(true);
         *
         * // Declarando variavel para faze os testes //conexao =
         * ModuloConexao.conector(); boolean decisao = false; if (decisao ==
         * true) { // mudando a cor do status
         * lbl_carregamento_inicial.setForeground(Color.red); // mudando o nome
         * do status lbl_carregamento_inicial.setText("Erro Banco de Dados...");
         * // menssagem de erro apresentada JOptionPane.showMessageDialog(null,
         * "Erro de Banco: Verifique se o Banco Mysql esta iniciado ou se há
         * erro de autenticação \n Reinicie o sistema e tente novamente"); //
         * Botão de sair aparecer btn_sair_login.setVisible(true); // parando o
         * sistema break;
         *
         * } else if (jProgressBar_inicial.getValue() <= 15) {
         * lbl_carregamento_inicial.setForeground(Color.blue);
         * lbl_carregamento_inicial.setText("Carregando Banco de Dados...");
         *
         * } else if (jProgressBar_inicial.getValue() <= 50) {
         * lbl_carregamento_inicial.setForeground(Color.blue);
         * lbl_carregamento_inicial.setText("Carregando Orçamentos...");
         *
         * } else if (jProgressBar_inicial.getValue() <= 85) {
         * lbl_carregamento_inicial.setForeground(Color.blue);
         * lbl_carregamento_inicial.setText("Carregando Dados Empresa...");
         *
         * } else if (jProgressBar_inicial.getValue() <= 90) {
         * lbl_carregamento_inicial.setForeground(Color.blue);
         * lbl_carregamento_inicial.setText("Iniciando Sistema...");
         *
         * } else if (jProgressBar_inicial.getValue() == 100) {
         * lbl_carregamento_inicial.setForeground(Color.blue);
         * lbl_carregamento_inicial.setText("Iniciando Sistema..."); fechar();
         *
         *
         * break;
         *
         * }
         * }
         * } catch (Exception e) { JOptionPane.showMessageDialog(null, "erro: "
         * + e.toString()); } } }).start();
         */
    }

    private void setIcon() {
        // comando a baixo serve para trocar o icone da janela nos jframes
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/infinitysolucoes/icones/iconMarceneiro.png")));
    }

    /**
     * Creates new form TelaLogin
     */
    @SuppressWarnings("empty-statement")
    public TelaLogin() {
        initComponents();
        setIcon();
        Color redColor = new Color(222, 184, 135);
        //Mudar cor do fundo
        getContentPane().setBackground(redColor);
        carregar();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar_inicial = new javax.swing.JProgressBar();
        imagemInicial = new javax.swing.JLabel();
        lbl_carregamento_inicial = new javax.swing.JLabel();
        btn_sair_login = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(0, 0, 0));
        setUndecorated(true);

        jProgressBar_inicial.setStringPainted(true);

        imagemInicial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/marca.png"))); // NOI18N
        imagemInicial.setText("imagem inicial");

        lbl_carregamento_inicial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_carregamento_inicial.setText(".");

        btn_sair_login.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_sair_login.setText("Sair");
        btn_sair_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sair_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_carregamento_inicial, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                        .addComponent(btn_sair_login, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jProgressBar_inicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(imagemInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(imagemInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_carregamento_inicial)
                    .addComponent(btn_sair_login, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar_inicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(600, 306));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void btn_sair_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sair_loginActionPerformed
        // SAINDO DO SISTEMA
        System.exit(0);
    }//GEN-LAST:event_btn_sair_loginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sair_login;
    private javax.swing.JLabel imagemInicial;
    public javax.swing.JProgressBar jProgressBar_inicial;
    private javax.swing.JLabel lbl_carregamento_inicial;
    // End of variables declaration//GEN-END:variables
}
