/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infinitysolucoes.tela;

import br.com.infinitysolucoes.TelaConfig.TelaConf;
import br.com.infinitysolucoes.dal.ModuloConexao;
import br.com.infnitysolucoes.tratamentos.Backup;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Usuario
 */
public class TelaPrincipal extends javax.swing.JFrame {

    Connection conexao = null;

    public TelaPrincipal() {
        initComponents();

        //Mudar cor do fundo
        Color fundoColor = new Color(228,228,229);
        getContentPane().setBackground(fundoColor);
        //jPanelMenuPrincipal.setBackground(redColor);
        //jPanel_cadastro.setBackground(redColor);
        //jPanel_relatorio.setBackground(redColor);
        //jPanel_servico.setBackground(redColor);
        //jPanelData.setBackground(redColor);

        // CHAMAR FUNÇÃO PARA CARREGAR ICONE DO SYSTEMA NO CANTO ESQUERDO SUPERIOR
        setIcon();
        // CARREGANDO DATA
        LocalDate dataCarregada = LocalDate.now();
        DateTimeFormatter fmt_data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataCerta = fmt_data.format(dataCarregada);
        title_data.setText(dataCerta);
        conexao = ModuloConexao.conector();
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Configure o Banco de Dados MySQL");
            btn_orc.setEnabled(false);
            btn_recibo.setEnabled(false);
            btn_orc.setEnabled(false);
            btn_cad_empresa.setEnabled(false);
            btn_cad_cliente.setEnabled(false);
            btn_servico_Relaberto.setEnabled(false);
            btn_servico_Relcliente.setEnabled(false);
            btn_servico_Relrealizado.setEnabled(false);
            
        }

        //carregamento();
    }

    // FUNÇÃO PARA SETAR IMAGEM DO ICONE
    private void setIcon() {
        // comando a baixo serve para trocar o icone da janela nos jframes
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/infinitysolucoes/icones/iconMarceneiro.png")));
    }

    private void relatorioClientes() {

        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desse relatório", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            try {
                // usando a classe jasperPrint para prepara a impessao de um latorio
                JasperPrint print = JasperFillManager.fillReport("C:\\Marceneiro\\formulario\\relCliente.jasper", null, conexao);
                //a linha abaxo exibe o relatorio 
                JasperViewer.viewReport(print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }catch(ExceptionInInitializerError ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    private void carrgegamento() {

        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMenuPrincipal = new javax.swing.JPanel();
        jPanel_cadastro = new javax.swing.JPanel();
        btn_cad_empresa = new javax.swing.JButton();
        btn_cad_cliente = new javax.swing.JButton();
        jPanel_servico = new javax.swing.JPanel();
        btn_orc = new javax.swing.JButton();
        jPanel_relatorio = new javax.swing.JPanel();
        btn_servico_Relcliente = new javax.swing.JButton();
        btn_servico_Relrealizado = new javax.swing.JButton();
        btn_servico_Relaberto = new javax.swing.JButton();
        btn_config = new javax.swing.JButton();
        btn_recibo = new javax.swing.JButton();
        btn_sair = new javax.swing.JButton();
        jPanelData = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        title_data = new javax.swing.JLabel();
        btnBackup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema - Marcenaria v 1.0");
        setBackground(new java.awt.Color(204, 255, 204));
        setResizable(false);

        jPanelMenuPrincipal.setBackground(new java.awt.Color(228, 228, 229));
        jPanelMenuPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")), "Menu Principal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jPanel_cadastro.setBackground(new java.awt.Color(228, 228, 229));
        jPanel_cadastro.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADASTRO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N
        jPanel_cadastro.setName(""); // NOI18N

        btn_cad_empresa.setBackground(new java.awt.Color(255, 255, 51));
        btn_cad_empresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_cad_empresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/cad_empresa.png"))); // NOI18N
        btn_cad_empresa.setText("CONF. EMPRESA");
        btn_cad_empresa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_cad_empresa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cad_empresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_cad_empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cad_empresaActionPerformed(evt);
            }
        });

        btn_cad_cliente.setBackground(new java.awt.Color(255, 255, 51));
        btn_cad_cliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_cad_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/cad_cliente.png"))); // NOI18N
        btn_cad_cliente.setText("CADASTRAR CLIENTES");
        btn_cad_cliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cad_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cad_clienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_cadastroLayout = new javax.swing.GroupLayout(jPanel_cadastro);
        jPanel_cadastro.setLayout(jPanel_cadastroLayout);
        jPanel_cadastroLayout.setHorizontalGroup(
            jPanel_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_cad_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
            .addComponent(btn_cad_empresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_cadastroLayout.setVerticalGroup(
            jPanel_cadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_cadastroLayout.createSequentialGroup()
                .addComponent(btn_cad_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cad_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel_servico.setBackground(new java.awt.Color(228, 228, 229));
        jPanel_servico.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SERVIÇOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        btn_orc.setBackground(new java.awt.Color(255, 255, 51));
        btn_orc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_orc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/orcamento.png"))); // NOI18N
        btn_orc.setText("ORÇAMENTO");
        btn_orc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_orcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_servicoLayout = new javax.swing.GroupLayout(jPanel_servico);
        jPanel_servico.setLayout(jPanel_servicoLayout);
        jPanel_servicoLayout.setHorizontalGroup(
            jPanel_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_orc, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        jPanel_servicoLayout.setVerticalGroup(
            jPanel_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel_relatorio.setBackground(new java.awt.Color(228, 228, 229));
        jPanel_relatorio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "IMPRIMIR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        btn_servico_Relcliente.setBackground(new java.awt.Color(255, 255, 51));
        btn_servico_Relcliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_servico_Relcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/rel_cliente.png"))); // NOI18N
        btn_servico_Relcliente.setText("RELATÓRIOS DE CLIENTES");
        btn_servico_Relcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_servico_Relcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_servico_RelclienteActionPerformed(evt);
            }
        });

        btn_servico_Relrealizado.setBackground(new java.awt.Color(255, 255, 51));
        btn_servico_Relrealizado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_servico_Relrealizado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/rel_servico_fechado.png"))); // NOI18N
        btn_servico_Relrealizado.setText("ORÇAMENTO FECHADO");
        btn_servico_Relrealizado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_servico_Relrealizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_servico_RelrealizadoActionPerformed(evt);
            }
        });

        btn_servico_Relaberto.setBackground(new java.awt.Color(255, 255, 51));
        btn_servico_Relaberto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_servico_Relaberto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/rel_servico_aberto.png"))); // NOI18N
        btn_servico_Relaberto.setText("SERVIÇOS EM ABERTO");
        btn_servico_Relaberto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_servico_Relaberto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_servico_RelabertoActionPerformed(evt);
            }
        });

        btn_config.setBackground(new java.awt.Color(153, 255, 102));
        btn_config.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_config.setForeground(new java.awt.Color(51, 51, 255));
        btn_config.setText("CONFIGURAÇÃO");
        btn_config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configActionPerformed(evt);
            }
        });

        btn_recibo.setBackground(new java.awt.Color(255, 255, 51));
        btn_recibo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_recibo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/recibo.png"))); // NOI18N
        btn_recibo.setText("RECIBO");
        btn_recibo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_recibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reciboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_relatorioLayout = new javax.swing.GroupLayout(jPanel_relatorio);
        jPanel_relatorio.setLayout(jPanel_relatorioLayout);
        jPanel_relatorioLayout.setHorizontalGroup(
            jPanel_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_servico_Relcliente, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
            .addComponent(btn_servico_Relrealizado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_servico_Relaberto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_config, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_recibo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_relatorioLayout.setVerticalGroup(
            jPanel_relatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_relatorioLayout.createSequentialGroup()
                .addComponent(btn_servico_Relcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_servico_Relrealizado, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_servico_Relaberto, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_recibo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_config, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btn_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/exclui_orca.png"))); // NOI18N
        btn_sair.setToolTipText("Sair do Sistema");
        btn_sair.setBorder(null);
        btn_sair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sairActionPerformed(evt);
            }
        });

        jPanelData.setBackground(new java.awt.Color(228, 228, 229));
        jPanelData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("DATA:");

        title_data.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        title_data.setText("jLabel3");

        javax.swing.GroupLayout jPanelDataLayout = new javax.swing.GroupLayout(jPanelData);
        jPanelData.setLayout(jPanelDataLayout);
        jPanelDataLayout.setHorizontalGroup(
            jPanelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(title_data)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanelDataLayout.setVerticalGroup(
            jPanelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(title_data))
        );

        btnBackup.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBackup.setText("BACKUP SISTEMA");
        btnBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMenuPrincipalLayout = new javax.swing.GroupLayout(jPanelMenuPrincipal);
        jPanelMenuPrincipal.setLayout(jPanelMenuPrincipalLayout);
        jPanelMenuPrincipalLayout.setHorizontalGroup(
            jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                        .addComponent(jPanel_servico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(btnBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                        .addComponent(jPanel_relatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(172, 172, 172)
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(jPanel_cadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMenuPrincipalLayout.setVerticalGroup(
            jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                        .addComponent(jPanel_servico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_cadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel_relatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(btnBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                        .addComponent(jPanelData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_cadastro.getAccessibleContext().setAccessibleName("CADASTROS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelMenuPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelMenuPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1216, 699));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cad_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cad_clienteActionPerformed
        // ABRIR TELA CAD CLIENTE
        TelaCadCliente Tcliente = new TelaCadCliente();
        Tcliente.setVisible(true);

    }//GEN-LAST:event_btn_cad_clienteActionPerformed

    private void btn_cad_empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cad_empresaActionPerformed
        //chamar tela cadastro empresa

        TelaCadEmpresa TcadEmpresa = new TelaCadEmpresa();
        TcadEmpresa.setVisible(true);
    }//GEN-LAST:event_btn_cad_empresaActionPerformed

    private void btn_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sairActionPerformed
        int sair = JOptionPane.showConfirmDialog(null, "Tem Certeza que deseja sair", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btn_sairActionPerformed

    private void btn_reciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reciboActionPerformed
        // ENTRAR NA JANELA RECIBO
        TelaRecibo Trecibo = new TelaRecibo();
        Trecibo.setVisible(true);
    }//GEN-LAST:event_btn_reciboActionPerformed

    private void btn_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_orcActionPerformed
        // ENTRAR NA JANELA ORÇAMENTO
        TelaOrcamento Torcamento = new TelaOrcamento();
        Torcamento.setVisible(true);
    }//GEN-LAST:event_btn_orcActionPerformed

    private void btn_servico_RelclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_servico_RelclienteActionPerformed
        // Imprimir Clientes 
        relatorioClientes();
    }//GEN-LAST:event_btn_servico_RelclienteActionPerformed

    private void btn_servico_RelrealizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_servico_RelrealizadoActionPerformed
        // IMPRIMR RELATORIO DE SERVIÇOS FECHADOS
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desse relatório", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            try {
                // usando a classe jasperPrint para prepara a impessao de um latorio
                JasperPrint print = JasperFillManager.fillReport("C:\\Marceneiro\\formulario\\orcFechados.jasper", null, conexao);
                //a linha abaxo exibe o relatorio 
                JasperViewer.viewReport(print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btn_servico_RelrealizadoActionPerformed

    private void btn_servico_RelabertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_servico_RelabertoActionPerformed
        // IMPRIMIR ORÇAMENTO EM ABERTA
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desse relatório", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            try {
                // usando a classe jasperPrint para prepara a impessao de um latorio
                JasperPrint print = JasperFillManager.fillReport("C:\\Marceneiro\\formulario\\orcAberto.jasper", null, conexao);
                //a linha abaxo exibe o relatorio 
                JasperViewer.viewReport(print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btn_servico_RelabertoActionPerformed

    private void btn_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configActionPerformed
        // ABRIR JANELA CONFIGURAÇÕES
        TelaConf telaconfig = new TelaConf();
        telaconfig.setVisible(true);


    }//GEN-LAST:event_btn_configActionPerformed

    private void btnBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackupActionPerformed
        Backup bk = new Backup();
        bk.setVisible(true);
    }//GEN-LAST:event_btnBackupActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackup;
    private javax.swing.JButton btn_cad_cliente;
    private javax.swing.JButton btn_cad_empresa;
    private javax.swing.JButton btn_config;
    private javax.swing.JButton btn_orc;
    private javax.swing.JButton btn_recibo;
    private javax.swing.JButton btn_sair;
    private javax.swing.JButton btn_servico_Relaberto;
    private javax.swing.JButton btn_servico_Relcliente;
    private javax.swing.JButton btn_servico_Relrealizado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelData;
    private javax.swing.JPanel jPanelMenuPrincipal;
    private javax.swing.JPanel jPanel_cadastro;
    private javax.swing.JPanel jPanel_relatorio;
    private javax.swing.JPanel jPanel_servico;
    private javax.swing.JLabel title_data;
    // End of variables declaration//GEN-END:variables

    
    
}

