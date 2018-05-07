
package br.com.infinitysolucoes.tela;
import br.com.infinitysolucoes.dal.ModuloConexao;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.proteanit.sql.DbUtils;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe Augusto da Silva Santos
 * Programador Júnior
 */
public class TelaCadCliente extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public TelaCadCliente() {
        initComponents();
        setIcon();
        // Preparar conexão
        conexao = ModuloConexao.conector();
    }
    // ###########################################################################
    // MÉTODO PRA ATUALIZA CLIENTE     ###########################################
    // ###########################################################################
    private void atualizarCliente(){
         // SETANDO A QUERY DE ADICIONAR CADASTRO
        String sql = "update tb_clientes set nome_cli= ?, cnpj_cpf_cli= ?,fone_cli=?, email_cli= ?, end_cli= ? where id_cli =?";
        
        try {
            
            // prepara conexao
            pst = conexao.prepareStatement(sql);
            // pegando campos e savando
            pst.setString(1, cliente_nome.getText());
            pst.setString(2, cliente_cnpj_cpf.getText());
            pst.setString(3, cliente_telefone.getText());
            pst.setString(4, cliente_email.getText());
            pst.setString(5, cliente_endereco.getText());
            pst.setString(6, cliente_id.getText());
            
// Validação dos campos obrigatorio
            // isEmpty verifica se estiver vasio
            if ((cliente_nome.getText().isEmpty() || cliente_cnpj_cpf.getText().isEmpty() || cliente_telefone.getText().isEmpty() || cliente_email.getText().isEmpty() || cliente_endereco.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, " * Preencha todos os campos Obigatorios");
            }else{
                // a linha abaixo atualiza a tabea usuário com o formulario do usuario
                //  ESTRUTURA A BAIXO É USADA PARA CONFIRMAR A INSERÇÃO DOS DADOS
                int adicionado = pst.executeUpdate();
                
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Atualizado com Sucesso");
                    // mudar configuração do botão
                    btn_cliente_salvar.setEnabled(true);
                    
                    // Limpar Campos
                    cliente_nome.setText(null);
                    cliente_cnpj_cpf.setText(null);
                    cliente_telefone.setText(null);
                    cliente_email.setText(null);
                    cliente_endereco.setText(null);
                    cliente_id.setText(null);
                    txtPesquisarCliente.setText(null);
                  
                }else{
                    JOptionPane.showMessageDialog(null, "Erro ao Gravar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    private void setIcon() {
        // comando a baixo serve para trocar o icone da janela nos jframes
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/infinitysolucoes/icones/iconMarceneiro.png")));
    }
    
    // ###########################################################################
    // MÉTODO PARA DELETAR CLIENTE     ###########################################
    // ###########################################################################
    private void deletarCliente(){
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este cliente", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tb_clientes where id_cli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, cliente_id.getText());

                int apagado = pst.executeUpdate();
                // Mensagem de deletado
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente removido com Sucesso");
                    // Limpando os campos após deletar Cliente
                    cliente_nome.setText(null);
                    cliente_cnpj_cpf.setText(null);
                    cliente_telefone.setText(null);
                    cliente_email.setText(null);
                    cliente_endereco.setText(null);
                    cliente_id.setText(null);
                    txtPesquisarCliente.setText(null);
                    // Desabiltar Botão
                    btn_cliente_atualizar.setEnabled(false);
                    btn_cliente_salvar.setEnabled(true);
                    btn_novo_cliente.setEnabled(false);
                    btn_cliente_deletar.setEnabled(false);
                   
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        
        }
    }  
    // ###########################################################################
    // MÉTODO BOTÃO DE NOVO CLIENTE    ###########################################
    // ###########################################################################
    private void novoCliente(){
        // Limpar Campos
                    cliente_nome.setText(null);
                    cliente_cnpj_cpf.setText(null);
                    cliente_telefone.setText(null);
                    cliente_email.setText(null);
                    cliente_endereco.setText(null);
                    cliente_id.setText(null);
                    txtPesquisarCliente.setText(null);
                    // setar modo dos botões
                    btn_cliente_salvar.setEnabled(true);
                    btn_novo_cliente.setEnabled(false);
                    btn_cliente_atualizar.setEnabled(false);
                    btn_cliente_deletar.setEnabled(false);
    }
    
    // ###########################################################################
    // MÉTODO PARA SETAR CLIENTE          ########################################
    // ###########################################################################
    public void setar_campos() {
        int setar = tbClientes.getSelectedRow();

        cliente_id.setText(tbClientes.getModel().getValueAt(setar, 0).toString());
        cliente_nome.setText(tbClientes.getModel().getValueAt(setar, 1).toString());
        cliente_cnpj_cpf.setText(tbClientes.getModel().getValueAt(setar, 2).toString());
        cliente_telefone.setText(tbClientes.getModel().getValueAt(setar, 3).toString());
        cliente_email.setText(tbClientes.getModel().getValueAt(setar, 4).toString());
        cliente_endereco.setText(tbClientes.getModel().getValueAt(setar, 5).toString());
        
        btn_cliente_atualizar.setEnabled(true);
        btn_cliente_deletar.setEnabled(true);
        btn_cliente_salvar.setEnabled(false);
        btn_novo_cliente.setEnabled(true);
    }
    // ###########################################################################
    // MÉTODO PARA PESQUISAR CLIENTE       #######################################
    // ###########################################################################
    private void pesquisar_cliente() {
        String sql = "select * from tb_clientes where nome_cli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa para o ?
            // atenção ao "%" que é  continuação da string sql
            pst.setString(1, txtPesquisarCliente.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tbClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // ###########################################################################
    // MÉTODO PARA SALVAR CLIENTE     ############################################
    // ###########################################################################
    private void salvarCliente() {
        
        // SETANDO A QUERY DE ADICIONAR CADASTRO
        String sql = "INSERT INTO tb_clientes (nome_cli, cnpj_cpf_cli, fone_cli, email_cli, end_cli) VALUES (?, ?,?,?,?)";
        
        try {
            // prepara conexao
            pst = conexao.prepareStatement(sql);
            // pegando campos e salvando
            pst.setString(1, cliente_nome.getText());
            pst.setString(2, cliente_cnpj_cpf.getText());
            pst.setString(3, cliente_telefone.getText());
            pst.setString(4, cliente_email.getText());
            pst.setString(5, cliente_endereco.getText());
            
            
            // Validação dos campos obrigatorio
            // isEmpty verifica se estiver vasio
            if (( cliente_nome.getText().isEmpty() || cliente_cnpj_cpf.getText().isEmpty() || cliente_telefone.getText().isEmpty() || cliente_email.getText().isEmpty() || cliente_endereco.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, " * Preencha todos os campos Obigatorios");
            }else{
                // a linha abaixo atualiza a tabea usuário com o formulario do usuario
                //  ESTRUTURA A BAIXO É USADA PARA CONFIRMAR A INSERÇÃO DOS DADOS
                int adicionado = pst.executeUpdate();
                
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");
                    // setar modo dos botões
                    btn_cliente_deletar.setEnabled(false);
                    
                    // limpar campos depois de atualizado
                    cliente_nome.setText(null);
                    cliente_cnpj_cpf.setText(null);
                    cliente_telefone.setText(null);
                    cliente_email.setText(null);
                    cliente_endereco.setText(null);
                    cliente_id.setText(null);
                    txtPesquisarCliente.setText(null);
                  
                }else{
                    JOptionPane.showMessageDialog(null, "Erro ao Gravar");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERRO AO SALVAR NO BANCO" +e.toString());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_dados_cliente = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cliente_nome = new javax.swing.JTextField();
        cliente_cnpj_cpf = new javax.swing.JTextField();
        cliente_telefone = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter telefone= new javax.swing.text.MaskFormatter("##-#####-####");
            cliente_telefone = new javax.swing.JFormattedTextField(telefone);
        }
        catch (Exception e){
        }
        cliente_endereco = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cliente_id = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cliente_email = new javax.swing.JTextField();
        btn_cliente_deletar = new javax.swing.JButton();
        btn_cliente_salvar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtPesquisarCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbClientes = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btn_cadCliente_voltar = new javax.swing.JButton();
        btn_cliente_atualizar = new javax.swing.JButton();
        btn_novo_cliente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Cliente");
        setResizable(false);

        jPanel_dados_cliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DADOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("NOME/EMPRESA *");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("CNPJ/CPF *");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("TELEFONE *");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("ENDEREÇO *");

        cliente_nome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cliente_cnpj_cpf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cliente_telefone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cliente_endereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("ID");

        cliente_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cliente_id.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("EMAIL *");

        cliente_email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel_dados_clienteLayout = new javax.swing.GroupLayout(jPanel_dados_cliente);
        jPanel_dados_cliente.setLayout(jPanel_dados_clienteLayout);
        jPanel_dados_clienteLayout.setHorizontalGroup(
            jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel_dados_clienteLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cliente_nome)
                    .addGroup(jPanel_dados_clienteLayout.createSequentialGroup()
                        .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cliente_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cliente_email, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cliente_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cliente_cnpj_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cliente_id, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_dados_clienteLayout.setVerticalGroup(
            jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_dados_clienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cliente_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cliente_cnpj_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cliente_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(cliente_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cliente_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dados_clienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cliente_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71))
        );

        btn_cliente_deletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/excluir_cli.png"))); // NOI18N
        btn_cliente_deletar.setToolTipText("Excluir");
        btn_cliente_deletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cliente_deletar.setEnabled(false);
        btn_cliente_deletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cliente_deletarActionPerformed(evt);
            }
        });

        btn_cliente_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/salvar_cli.png"))); // NOI18N
        btn_cliente_salvar.setToolTipText("Salvar");
        btn_cliente_salvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cliente_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cliente_salvarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PESQUISAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        txtPesquisarCliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtPesquisarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarClienteActionPerformed(evt);
            }
        });
        txtPesquisarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarClienteKeyReleased(evt);
            }
        });

        tbClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Cpf/Cjpj", "Fone", "Email"
            }
        ));
        tbClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbClientes);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/procurar.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtPesquisarCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtPesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btn_cadCliente_voltar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_cadCliente_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/voltar_menu.png"))); // NOI18N
        btn_cadCliente_voltar.setText("VOLTAR ");
        btn_cadCliente_voltar.setToolTipText("Voltar");
        btn_cadCliente_voltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cadCliente_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadCliente_voltarActionPerformed(evt);
            }
        });

        btn_cliente_atualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/atualizar_cli2.png"))); // NOI18N
        btn_cliente_atualizar.setToolTipText("Atualizar");
        btn_cliente_atualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cliente_atualizar.setEnabled(false);
        btn_cliente_atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cliente_atualizarActionPerformed(evt);
            }
        });

        btn_novo_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/create.png"))); // NOI18N
        btn_novo_cliente.setToolTipText("Adicionar novo");
        btn_novo_cliente.setEnabled(false);
        btn_novo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_novo_clienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_dados_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_novo_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_cliente_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cliente_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cliente_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cadCliente_voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_dados_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_cadCliente_voltar, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(btn_cliente_salvar, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(btn_cliente_deletar, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(btn_cliente_atualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(btn_novo_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        setSize(new java.awt.Dimension(840, 588));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cadCliente_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadCliente_voltarActionPerformed
        //BOTÃO VOLTAR
        this.dispose();
    }//GEN-LAST:event_btn_cadCliente_voltarActionPerformed

    private void btn_cliente_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cliente_salvarActionPerformed
        // MÉTODO SALVAR CLIENTE
        salvarCliente();
    }//GEN-LAST:event_btn_cliente_salvarActionPerformed

    private void txtPesquisarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarClienteKeyReleased
        // PESQUISAR CLIENTE ENQUANTO VOCÊ DIGITA
        pesquisar_cliente();
    }//GEN-LAST:event_txtPesquisarClienteKeyReleased

    private void tbClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbClientesMouseClicked
        // MÉTODO QUANDO CLICAR/ESCOLHER NO USUARIO NA TABELA.
        setar_campos();
    }//GEN-LAST:event_tbClientesMouseClicked

    private void btn_cliente_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cliente_atualizarActionPerformed
        // CHAMAR MÉTODO ATUALIZAR CLIENTE
        atualizarCliente();
    }//GEN-LAST:event_btn_cliente_atualizarActionPerformed

    private void btn_novo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_novo_clienteActionPerformed
        // CHAMAR MÉTODO NOVO CLIENTE
        novoCliente();
    }//GEN-LAST:event_btn_novo_clienteActionPerformed

    private void btn_cliente_deletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cliente_deletarActionPerformed
        // MÉTODO DE DELETAR CLIENTE
        deletarCliente();
    }//GEN-LAST:event_btn_cliente_deletarActionPerformed

    private void txtPesquisarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisarClienteActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cadCliente_voltar;
    private javax.swing.JButton btn_cliente_atualizar;
    private javax.swing.JButton btn_cliente_deletar;
    private javax.swing.JButton btn_cliente_salvar;
    private javax.swing.JButton btn_novo_cliente;
    private javax.swing.JTextField cliente_cnpj_cpf;
    private javax.swing.JTextField cliente_email;
    private javax.swing.JTextField cliente_endereco;
    private javax.swing.JTextField cliente_id;
    private javax.swing.JTextField cliente_nome;
    private javax.swing.JTextField cliente_telefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_dados_cliente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbClientes;
    private javax.swing.JTextField txtPesquisarCliente;
    // End of variables declaration//GEN-END:variables
}
