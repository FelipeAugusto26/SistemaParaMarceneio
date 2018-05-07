/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infinitysolucoes.tela;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.com.infinitysolucoes.dal.ModuloConexao;
import java.awt.Toolkit;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe Augusto
 */
public class TelaCadEmpresa extends javax.swing.JFrame {
    // VAIAVEIS DE CONEXAO E PREPARAÇÃO
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
   
    
        
    public TelaCadEmpresa() {
        initComponents();
        // ATRIBUIR A VARIAVEL CONEXAO O MÉTODO CONECTOR DO BANCO
        conexao = ModuloConexao.conector();
        
        setIcon();
        
        // DESABILITAR CAMPOS AO CARREGAR JANELA
        cad_nomeFantasia.setEnabled(false);
        cad_cnpj.setEnabled(false);
        cad_telefone.setEnabled(false);
        cad_endereco.setEnabled(false);
        cad_email.setEnabled(false);
        cad_diaria.setEnabled(false);
        cad_lucro.setEnabled(false);
        valor_mdf_branco.setEnabled(false);
        valor_mdf_brilhoso.setEnabled(false);
        valor_mdf_maderado.setEnabled(false);
        
        // DESABILITAR AO CARREGAR ALGUNS BOTÕES
        btn_atualizar_empresa.setEnabled(false);
        // CARREGAR EMPRESA AO ABRIR JANELA
        // setando a Query 
        String sql = "select * from tb_empresa where id=?";
        // identificação da empresa cadastrada
        try {
            String idEmpresa = "1";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idEmpresa);
            rs = pst.executeQuery();
            if(rs.next()){
                cad_nomeFantasia.setText(rs.getString(2));
                cad_cnpj.setText(rs.getString(3));
                cad_telefone.setText(rs.getString(4));
                cad_endereco.setText(rs.getString(5));
                cad_email.setText(rs.getString(6));
                cad_diaria.setText(rs.getString(7));
                cad_lucro.setText(rs.getString(8));
                valor_mdf_branco.setText(rs.getString(9));
                valor_mdf_brilhoso.setText(rs.getString(10));
                valor_mdf_maderado.setText(rs.getString(11));
                
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        
    }
    
    private void setIcon() {
        // comando a baixo serve para trocar o icone da janela nos jframes
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/infinitysolucoes/icones/iconMarceneiro.png")));
    }
   
    private void atualizarEmpresa(){
         // SETANDO A QUERY DE ADICIONAR CADASTRO
        String sql = "update tb_empresa set nome_fantasia= ?, cnpj= ?,fone=?, end=?, email=?, valor_dia_trab=?,marg_lucro=?, mdf_branco = ?, mdf_brilhoso = ?, mdf_maderado = ? where id =?";
        
        try {
            
            // prepara conexao
            pst = conexao.prepareStatement(sql);
            // pegando campos e savando
            pst.setString(1, cad_nomeFantasia.getText());
            pst.setString(2, cad_cnpj.getText());
            pst.setString(3, cad_telefone.getText());
            pst.setString(4, cad_endereco.getText());
            pst.setString(5, cad_email.getText());
            pst.setString(6, cad_diaria.getText());
            pst.setString(7, cad_lucro.getText());
            pst.setString(8, valor_mdf_branco.getText());
            pst.setString(9, valor_mdf_brilhoso.getText());
            pst.setString(10, valor_mdf_maderado.getText());
            pst.setString(11, id_empresa.getText());
// Validação dos campos obrigatorio
            // isEmpty verifica se estiver vasio
            if ((cad_nomeFantasia.getText().isEmpty() || cad_cnpj.getText().isEmpty() || cad_endereco.getText().isEmpty() || cad_telefone.getText().isEmpty() || cad_email.getText().isEmpty() || cad_diaria.getText().isEmpty() || cad_lucro.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, " * Preencha todos os campos Obigatorios");
            }else{
                // a linha abaixo atualiza a tabea usuário com o formulario do usuario
                //  ESTRUTURA A BAIXO É USADA PARA CONFIRMAR A INSERÇÃO DOS DADOS
                int adicionado = pst.executeUpdate();
                
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");
                  
                }else{
                    JOptionPane.showMessageDialog(null, "Erro ao Gravar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        cad_nomeFantasia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cad_cnpj = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter telefone= new javax.swing.text.MaskFormatter("##.###.###/####-##");
            cad_cnpj = new javax.swing.JFormattedTextField(telefone);
        }
        catch (Exception e){
        }
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cad_telefone = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter telefone= new javax.swing.text.MaskFormatter("(##)-# ####-####");
            cad_telefone = new javax.swing.JFormattedTextField(telefone);
        }
        catch (Exception e){
        }
        cad_endereco = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cad_email = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cad_diaria = new javax.swing.JTextField();
        cad_lucro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        valor_mdf_maderado = new javax.swing.JTextField();
        valor_mdf_brilhoso = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        valor_mdf_branco = new javax.swing.JTextField();
        btn_atualizar_empresa = new javax.swing.JButton();
        btn_edit_empresa = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        id_empresa = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Empresa");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("NOME FANTASIA*");

        cad_nomeFantasia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cad_nomeFantasia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cad_nomeFantasiaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("CNPJ*");

        cad_cnpj.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cad_cnpj.setToolTipText("00.000.000/0000-00");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("TELFONE*");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("ENDEREÇO*");

        cad_telefone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cad_telefone.setToolTipText("99-99999-9999");

        cad_endereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("EMAIL*");

        cad_email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cad_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cad_emailActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preferências", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 0, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 0, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("DIÁRIA (R$)*");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("M. DE LUCRO (%)*");

        cad_diaria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cad_diaria.setText("0");

        cad_lucro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cad_lucro.setText("0");
        cad_lucro.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("V. MDF BRANCO(R$)");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("V. MDF BRILHOSO(R$)");

        valor_mdf_maderado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        valor_mdf_maderado.setText("0");

        valor_mdf_brilhoso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        valor_mdf_brilhoso.setText("0");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("V. MDF MADERADO(R$)");

        valor_mdf_branco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        valor_mdf_branco.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cad_diaria, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cad_lucro, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(valor_mdf_maderado, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valor_mdf_branco, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(valor_mdf_brilhoso, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cad_diaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(valor_mdf_maderado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(valor_mdf_branco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cad_lucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(valor_mdf_brilhoso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_atualizar_empresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/atualizar_cli2.png"))); // NOI18N
        btn_atualizar_empresa.setToolTipText("Atualizar Cadastro");
        btn_atualizar_empresa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_atualizar_empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizar_empresaActionPerformed(evt);
            }
        });

        btn_edit_empresa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_edit_empresa.setText("EDITAR EMPRESA");
        btn_edit_empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edit_empresaActionPerformed(evt);
            }
        });

        jLabel1.setText("ID");

        id_empresa.setText("1");
        id_empresa.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cad_email, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(463, 463, 463))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cad_nomeFantasia, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cad_telefone, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cad_cnpj, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cad_endereco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addContainerGap())))
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_atualizar_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_edit_empresa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(136, 136, 136))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(id_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cad_nomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cad_cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cad_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cad_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cad_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_atualizar_empresa)
                    .addComponent(btn_edit_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(832, 498));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cad_nomeFantasiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cad_nomeFantasiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cad_nomeFantasiaActionPerformed

    private void cad_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cad_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cad_emailActionPerformed

    private void btn_atualizar_empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizar_empresaActionPerformed
        // CHAMAR FUNÇÃO ATUALIAZAR
        atualizarEmpresa();
    }//GEN-LAST:event_btn_atualizar_empresaActionPerformed

    private void btn_edit_empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edit_empresaActionPerformed
        // HABLITA CAMPOS
        cad_nomeFantasia.setEnabled(true);
        cad_cnpj.setEnabled(true);
        cad_telefone.setEnabled(true);
        cad_endereco.setEnabled(true);
        cad_email.setEnabled(true);
        cad_diaria.setEnabled(true);
        cad_lucro.setEnabled(true);
        valor_mdf_branco.setEnabled(true);
        valor_mdf_brilhoso.setEnabled(true);
        valor_mdf_maderado.setEnabled(true);
        // HABILITAR AO CARREGAR ALGUNS BOTÕES
        btn_edit_empresa.setEnabled(true);
        //btn_salvar_empresa.setEnabled(true);
        
        btn_atualizar_empresa.setEnabled(true);
    }//GEN-LAST:event_btn_edit_empresaActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadEmpresa().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_atualizar_empresa;
    private javax.swing.JButton btn_edit_empresa;
    private javax.swing.JTextField cad_cnpj;
    private javax.swing.JTextField cad_diaria;
    private javax.swing.JTextField cad_email;
    private javax.swing.JTextField cad_endereco;
    private javax.swing.JTextField cad_lucro;
    private javax.swing.JTextField cad_nomeFantasia;
    private javax.swing.JTextField cad_telefone;
    private javax.swing.JTextField id_empresa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField valor_mdf_branco;
    private javax.swing.JTextField valor_mdf_brilhoso;
    private javax.swing.JTextField valor_mdf_maderado;
    // End of variables declaration//GEN-END:variables
}
