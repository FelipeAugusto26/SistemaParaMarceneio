/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infinitysolucoes.tela;

import br.com.infinitysolucoes.dal.ModuloConexao;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Usuario
 */
public class TelaOrcamento extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaOrcamento() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        // Mudar cor do fundo
        // Setando cor RGB
        Color redColor = new Color(169, 169, 169);
        // cor dos campos de digitação
        Color yellowColor = new Color(240, 230, 140);

        getContentPane().setBackground(redColor);
        jPanel_pesquisar_clientes.setBackground(redColor);
        jPanel_dados.setBackground(redColor);
        jPanel_total.setBackground(redColor);
        jPanel_indentificador.setBackground(redColor);
        jPanel_opções.setBackground(redColor);
        jPanelAtalho.setBackground(redColor);

        entrada_pesquisa_cliente.setBackground(Color.yellow);
        entrada_desc_servico_orc.setBackground(yellowColor);
        entrada_qtn_chapa.setBackground(yellowColor);
        entrada_qtn_diaTrabalado.setBackground(yellowColor);
        entrada_valor_material_orc.setBackground(yellowColor);
        entrada_pesquisa_orc.setBackground(Color.YELLOW);

        // CARREGANDO DATA
        LocalDate dataCarregada = LocalDate.now();
        DateTimeFormatter fmt_data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataCerta = fmt_data.format(dataCarregada);
        txtData.setText(dataCerta);

    }

    private void setIcon() {
        // comando a baixo serve para trocar o icone da janela nos jframes
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/infinitysolucoes/icones/iconMarceneiro.png")));
    }

    // ###########################################################################
    // MÉTODO PARA NOVO ORÇAMENTO   ##############################################
    // ###########################################################################
    private void novoOrcamento() {
        // BLOQUEAR ENTRADAS
        jComboBox_ambiente.setEnabled(false);
        jComboBox_situacao.setEnabled(false);
        jComboBox_tipo_mdf_orc.setEnabled(false);
        entrada_qtn_chapa.setEnabled(false);
        entrada_qtn_diaTrabalado.setEnabled(false);
        entrada_valor_material_orc.setEnabled(false);
        entrada_desc_servico_orc.setEnabled(false);
        // ABLITANDO TABELA CLIENTE
        jTable_cliente_orc.setEnabled(true);
        entrada_pesquisa_cliente.setEnabled(true);
        entrada_pesquisa_orc.setEnabled(true);
        jTable_orc.setEnabled(true);

        // DESABILITAR BOTÃO
        btn_salvar_orc.setEnabled(false);

        //LIMPANDO CAMPOS
        entrada_nome_orc.setText(null);
        entrada_telefone_orc.setText(null);
        entrada_endereco_orc.setText(null);
        jComboBox_situacao.setSelectedItem("ORÇAMENTO EM ABERTO");
        jComboBox_ambiente.setSelectedItem("QUARTO");
        entrada_desc_servico_orc.setText(null);
        entrada_valor_material_orc.setText(null);
        jComboBox_tipo_mdf_orc.setSelectedItem("BRANCO");
        entrada_id_cli.setText(null);
        entrada_valor_total_orc.setText(null);
        entrada_qtn_diaTrabalado.setText(null);
        entrada_qtn_chapa.setText(null);
        entrada_id_orcamento.setText(null);
        id_identifacao.setText(null);

    }
    // ###########################################################################
    // MÉTODO PARA IMPRMIR     ###################################################
    // ###########################################################################

    private void imprimirOrcamento() {
        String numeroOrc = entrada_id_orcamento.getText();
        if ("".equals(numeroOrc)) {
            JOptionPane.showMessageDialog(null, "Nenhum Orçamento Selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            if (jComboBox_situacao.getSelectedItem() == "ORÇAMENTO FECHADO") {
                HashMap filtro = new HashMap();
                filtro.put("id", Integer.parseInt(numeroOrc));// o nome "os" tem que ser o mesmo da dado  como parâmetro lá no JReport 
                try {
                    // usando a classe jasperPrint para prepara a impressao de um latorio
                    JasperPrint print = JasperFillManager.fillReport("C:\\Marceneiro\\formulario\\OrcamentoP.jasper", filtro, conexao);
                    //a linha abaxo exibe o relatorio 
                    JasperViewer.viewReport(print, false);
                } catch (JRException ex) {
                    JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR);
                }

            }else{
                JOptionPane.showMessageDialog(null, "Orçamento em aberto\nPor favor, feche o orçamento primeiro...", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    // ###########################################################################
    // MÉTODO PARA PESQUISAR CLIENTE       #######################################
    // ###########################################################################
    private void pesquisar_cliente() {
        String sql = "select id_cli as ID, nome_cli as Nome, fone_cli as Telefone, end_cli as Endereço from tb_clientes where nome_cli like ?";

        try {

            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa para o ?
            // atenção ao "%" que é  continuação da string sql
            pst.setString(1, entrada_pesquisa_cliente.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela            
            jTable_cliente_orc.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setar_campos() {
        try {
            int setar = jTable_cliente_orc.getSelectedRow();

            entrada_id_cli.setText(jTable_cliente_orc.getModel().getValueAt(setar, 0).toString());
            entrada_nome_orc.setText(jTable_cliente_orc.getModel().getValueAt(setar, 1).toString());
            entrada_telefone_orc.setText(jTable_cliente_orc.getModel().getValueAt(setar, 2).toString());
            entrada_endereco_orc.setText(jTable_cliente_orc.getModel().getValueAt(setar, 3).toString());

            // habiltando campos
            jComboBox_ambiente.setEnabled(true);
            jComboBox_situacao.setEnabled(true);
            entrada_desc_servico_orc.setEnabled(true);
            entrada_valor_material_orc.setEnabled(true);
            jComboBox_tipo_mdf_orc.setEnabled(true);
            entrada_qtn_diaTrabalado.setEnabled(true);
            entrada_qtn_chapa.setEnabled(true);
            btn_excluir_orc.setEnabled(false);
            entrada_pesquisa_orc.setEnabled(false);
            jTable_orc.setEnabled(false);
            btn_calc_orc.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\n- Aperte em Novo Orçamento\n- ou\n- Cancele o processo iniciado", "Tabela Bloquada", 1);
        }

    }

    private void salvarOrca() {
        // SETANDO A QUERY
        String sql = "insert into tb_orc (id_orc, nome_empresa, telefone_cli, endereco_cli, situacao_orc, ambiente_orc,descricao_orc, valor_material_orc,tipo_mdf_orc, id_cli, valor_total_orc, qtn_dias,qtn_chapa ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            // PREPARANO A CONEXAO
            pst = conexao.prepareStatement(sql);
            // SALVANDO INTENS
            pst.setString(1, entrada_id_orcamento.getText());
            pst.setString(2, entrada_nome_orc.getText());
            pst.setString(3, entrada_telefone_orc.getText());
            pst.setString(4, entrada_endereco_orc.getText());
            pst.setString(5, jComboBox_situacao.getSelectedItem().toString());
            pst.setString(6, jComboBox_ambiente.getSelectedItem().toString());
            pst.setString(7, entrada_desc_servico_orc.getText());
            pst.setString(8, entrada_valor_material_orc.getText());
            pst.setString(9, jComboBox_tipo_mdf_orc.getSelectedItem().toString());
            pst.setString(10, entrada_id_cli.getText());
            pst.setString(11, entrada_valor_total_orc.getText());
            pst.setString(12, entrada_qtn_diaTrabalado.getText());
            pst.setString(13, entrada_qtn_chapa.getText());

            // Verifica se alguns campos estão com erro
            if (entrada_desc_servico_orc.getText().isEmpty() || entrada_valor_material_orc.getText().isEmpty() || entrada_qtn_diaTrabalado.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os Campos");
            } else {
                // caso não tenha nada de errado ele executa a query de salvar
                JOptionPane.showMessageDialog(null, "Orçamento salvo com sucesso");

                // LIMPANDO CAMPOS
                entrada_nome_orc.setText(null);
                entrada_telefone_orc.setText(null);
                entrada_endereco_orc.setText(null);
                jComboBox_situacao.setSelectedItem(null);
                jComboBox_ambiente.setSelectedItem(null);
                entrada_desc_servico_orc.setText(null);
                entrada_valor_material_orc.setText(null);
                jComboBox_tipo_mdf_orc.setSelectedItem(null);
                entrada_id_cli.setText(null);
                entrada_valor_total_orc.setText(null);
                entrada_qtn_diaTrabalado.setText(null);
                entrada_qtn_chapa.setText(null);

                pst.executeUpdate();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    // ESCOLHER ORÇAMENTO
    private void escolherOrcamento() {

        String sql = "select * from tb_orc where id_orc=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, id_identifacao.getText());
            rs = pst.executeQuery();

            if (rs.next()) {

                // pegando variaveis do banco
                entrada_id_orcamento.setText(rs.getString(1));
                entrada_nome_orc.setText(rs.getString(2));
                entrada_telefone_orc.setText(rs.getString(3));
                entrada_endereco_orc.setText(rs.getString(4));
                jComboBox_situacao.setSelectedItem(rs.getString(6));
                jComboBox_ambiente.setSelectedItem(rs.getString(7));
                entrada_desc_servico_orc.setText(rs.getString(8));
                entrada_valor_material_orc.setText(rs.getString(9));
                jComboBox_tipo_mdf_orc.setSelectedItem(rs.getString(10));
                entrada_id_cli.setText(rs.getString(11));
                entrada_valor_total_orc.setText(rs.getString(12));
                entrada_qtn_diaTrabalado.setText(rs.getString(13));
                entrada_qtn_chapa.setText(rs.getString(14));
                // modo do botão
                btn_calc_orc_salvar.setEnabled(false);

                btn_excluir_orc.setEnabled(true);
                // desativando tabela cliente
                jTable_cliente_orc.setEnabled(false);
                entrada_pesquisa_cliente.setEnabled(false);
                btn_editar_orc.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Orçamento não encontrado\n Por favor, escolha um orçamento", "Erro", 1);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "erro", 1);
        }

    }

    // PESQUISAR ORCAMENTO
    private void pesquisar_orcamento() {
        String sql = "select data_orc as Data, id_orc as ID, nome_empresa as Nome, ambiente_orc as Ambiente, descricao_orc as Descrição from tb_orc where nome_empresa like ? or ambiente_orc like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa para o ?
            // atenção ao "%" que é  continuação da string sql
            pst.setString(1, entrada_pesquisa_orc.getText() + "%");
            pst.setString(2, entrada_pesquisa_orc.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            jTable_orc.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // CALCULAR ORÇAMENTO ANTES DE SALVAR
    private void calcularOrcamento() {
        // vaiaveis para receber dados
        String valorMdfBrilho;
        String valorMdfMadeirado;
        String valorMdfBranco;
        String valorDiaria;
        String MargemLucro;
        String qtn_dias = entrada_qtn_diaTrabalado.getText();
        String qtn_chapa = entrada_qtn_chapa.getText();
        String Vmaterial = entrada_valor_material_orc.getText();
        // VARIAVEIS PARA CALCULAR
        double MdfBrilho;
        double MdfBranco;
        double MdfMadeirado;
        int dia;
        double total;
        double diaria;
        int chapa;
        int margem;
        double parcial;
        double material;
        // CALCULAR ORÇAMENTO
        String sql = "select * from tb_empresa where id=?";
        // identificação da empresa cadastrada
        try {
            String idEmpresa = "1";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idEmpresa);
            rs = pst.executeQuery();
            if (rs.next()) {
                valorDiaria = rs.getString(7);
                MargemLucro = rs.getString(8);
                valorMdfBranco = rs.getString(9);
                valorMdfBrilho = rs.getString(10);
                valorMdfMadeirado = rs.getString(11);

                // Tranformando variaves para inteiras
                MdfBrilho = Double.parseDouble(valorMdfBrilho);
                MdfBranco = Double.parseDouble(valorMdfBranco);
                MdfMadeirado = Double.parseDouble(valorMdfMadeirado);
                dia = Integer.parseInt(qtn_dias);
                diaria = Double.parseDouble(valorDiaria);
                chapa = Integer.parseInt(qtn_chapa);
                margem = Integer.parseInt(MargemLucro);
                material = Double.parseDouble(Vmaterial);
                // DECISÕES DAS MADEIRAS
                if (jComboBox_tipo_mdf_orc.getSelectedItem() == "BRANCO") {
                    double V_diaria_Dia = dia * diaria;
                    double V_chapa = chapa * MdfBranco;
                    parcial = (V_diaria_Dia + V_chapa + material) * margem / 100;
                    total = V_diaria_Dia + V_chapa + parcial + material;
                    String TOTAL = Moeda.mascaraDinheiro(total, Moeda.DINHEIRO_REAL);
                    entrada_valor_total_orc.setText(TOTAL);
                    // Ativando botão de salvar
                    btn_salvar_orc.setEnabled(true);
                } else if (jComboBox_tipo_mdf_orc.getSelectedItem() == "MADEIRADO") {
                    double V_diaria_Dia = dia * diaria;
                    double V_chapa = chapa * MdfMadeirado;
                    parcial = (V_diaria_Dia + V_chapa) * margem / 100;
                    total = V_diaria_Dia + V_chapa + parcial + material;
                    String TOTAL = Moeda.mascaraDinheiro(total, Moeda.DINHEIRO_REAL);
                    entrada_valor_total_orc.setText(TOTAL);
                    // Ativando botão de salvar
                    btn_salvar_orc.setEnabled(true);
                } else if (jComboBox_tipo_mdf_orc.getSelectedItem() == "BRILHO") {
                    double V_diaria_Dia = dia * diaria;
                    double V_chapa = chapa * MdfBrilho;
                    parcial = (V_diaria_Dia + V_chapa) * margem / 100;
                    total = V_diaria_Dia + V_chapa + parcial + material;
                    String TOTAL = Moeda.mascaraDinheiro(total, Moeda.DINHEIRO_REAL);
                    entrada_valor_total_orc.setText(TOTAL);
                    // Ativando botão de salvar
                    btn_salvar_orc.setEnabled(true);
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " INSIRA UM CLIENTE E DADOS PARA CALCULAR\n " + e);
        }

    }

    private void calcularOrcamentoNovo() {
        // vaiaveis para receber dados
        String valorMdfBrilho;
        String valorMdfMadeirado;
        String valorMdfBranco;
        String valorDiaria;
        String MargemLucro;
        String qtn_dias = entrada_qtn_diaTrabalado.getText();
        String qtn_chapa = entrada_qtn_chapa.getText();
        String Vmaterial = entrada_valor_material_orc.getText();
        // VARIAVEIS PARA CALCULAR
        double MdfBrilho;
        double MdfBranco;
        double MdfMadeirado;
        int dia;
        double total;
        double diaria;
        int chapa;
        int margem;
        double parcial;
        double material;
        // CALCULAR ORÇAMENTO
        String sql = "select * from tb_empresa where id=?";
        // identificação da empresa cadastrada
        try {
            String idEmpresa = "1";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, idEmpresa);
            rs = pst.executeQuery();
            if (rs.next()) {
                valorDiaria = rs.getString(7);
                MargemLucro = rs.getString(8);
                valorMdfBranco = rs.getString(9);
                valorMdfBrilho = rs.getString(10);
                valorMdfMadeirado = rs.getString(11);

                // Tranformando variaves para inteiras
                MdfBrilho = Double.parseDouble(valorMdfBrilho);
                MdfBranco = Double.parseDouble(valorMdfBranco);
                MdfMadeirado = Double.parseDouble(valorMdfMadeirado);
                dia = Integer.parseInt(qtn_dias);
                diaria = Double.parseDouble(valorDiaria);
                chapa = Integer.parseInt(qtn_chapa);
                margem = Integer.parseInt(MargemLucro);
                material = Double.parseDouble(Vmaterial);
                // DECISÕES DAS MADEIRAS
                if (jComboBox_tipo_mdf_orc.getSelectedItem() == "BRANCO") {
                    double V_diaria_Dia = diaria * dia;
                    double V_chapa = MdfBranco * chapa;

                    parcial = (V_diaria_Dia + V_chapa + material) * margem / 100;
                    total = V_diaria_Dia + V_chapa + parcial + material;

                    String TOTAL = Moeda.mascaraDinheiro(total, Moeda.DINHEIRO_REAL);
                    entrada_valor_total_orc.setText(TOTAL);

                } else if (jComboBox_tipo_mdf_orc.getSelectedItem() == "MADEIRADO") {
                    double V_diaria_Dia = dia * diaria;
                    double V_chapa = chapa * MdfMadeirado;
                    parcial = (V_diaria_Dia + V_chapa) * margem / 100;
                    total = V_diaria_Dia + V_chapa + parcial + material;
                    String TOTAL = Moeda.mascaraDinheiro(total, Moeda.DINHEIRO_REAL);
                    entrada_valor_total_orc.setText(TOTAL);

                } else if (jComboBox_tipo_mdf_orc.getSelectedItem() == "BRILHO") {
                    double V_diaria_Dia = dia * diaria;
                    double V_chapa = chapa * MdfBrilho;
                    parcial = (V_diaria_Dia + V_chapa) * margem / 100;
                    total = V_diaria_Dia + V_chapa + parcial + material;
                    String TOTAL = Moeda.mascaraDinheiro(total, Moeda.DINHEIRO_REAL);
                    entrada_valor_total_orc.setText(TOTAL);

                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " INSIRA UM CLIENTE E DADOS PARA CALCULAR\n " + e);
        }

    }

    // CALCULAR ORÇAMENTO ANTES DE EDITAR
    private void EditarOrcamento() {
        // habilitando campos 
        // ENTRADAS
        jComboBox_ambiente.setEnabled(true);
        jComboBox_situacao.setEnabled(true);
        entrada_desc_servico_orc.setEnabled(true);
        entrada_valor_material_orc.setEnabled(true);
        jComboBox_tipo_mdf_orc.setEnabled(true);
        entrada_qtn_diaTrabalado.setEnabled(true);
        entrada_qtn_chapa.setEnabled(true);
        // BOTÕES
        btn_atualizar_orc.setEnabled(true);
        btn_imprimir_orc.setEnabled(true);
        btn_salvar_orc.setEnabled(false);

    }

    private void escolherTabela() {
        try {
            int selecionado = jTable_orc.getSelectedRow();
            // ESTANCIANDO A CLASSE, PARA PEGAR AS VARIÁVEIS 
            id_identifacao.setText(jTable_orc.getModel().getValueAt(selecionado, 1).toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tabela Bloqueada");

        }
    }
    // SALVAR ORÇAMENTO

    private void salvarOrcamento() {
        String sql = "INSERT INTO tb_orc(nome_empresa, telefone_cli, endereco_cli, situacao_orc, ambiente_orc, descricao_orc, valor_material_orc, tipo_mdf_orc, id_cli, valor_total_orc, qtn_dias, qtn_chapa)values(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            // preparando conexão
            pst = conexao.prepareStatement(sql);

            pst.setString(1, entrada_nome_orc.getText());
            pst.setString(2, entrada_telefone_orc.getText());
            pst.setString(3, entrada_endereco_orc.getText());
            pst.setString(4, jComboBox_situacao.getSelectedItem().toString());
            pst.setString(5, jComboBox_ambiente.getSelectedItem().toString());
            pst.setString(6, entrada_desc_servico_orc.getText());
            pst.setString(7, entrada_valor_material_orc.getText());
            pst.setString(8, jComboBox_tipo_mdf_orc.getSelectedItem().toString());
            pst.setString(9, entrada_id_cli.getText());
            pst.setString(10, entrada_valor_total_orc.getText());
            pst.setString(11, entrada_qtn_diaTrabalado.getText());
            pst.setString(12, entrada_qtn_chapa.getText());

            if (entrada_desc_servico_orc.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", 2);

            } else {
                int adicionado = pst.executeUpdate();
                // FERIFICAR SE FOI SALVO CORRETAMENTE
                // RETORNANDO O NUMERO MARO QUE 0
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Orçamento Salvo com Sucesso", "Salvo", 1);
                    // HABILTANDO CAMPOS / BOTÕES
                    entrada_pesquisa_orc.setEnabled(true);
                    jTable_orc.setEnabled(true);
                    btn_calc_orc.setEnabled(true);
                    // BLOQUEAR ENTRADAS
                    jComboBox_ambiente.setEnabled(false);
                    jComboBox_situacao.setEnabled(false);
                    jComboBox_tipo_mdf_orc.setEnabled(false);
                    entrada_qtn_chapa.setEnabled(false);
                    entrada_qtn_diaTrabalado.setEnabled(false);
                    entrada_valor_material_orc.setEnabled(false);
                    entrada_desc_servico_orc.setEnabled(false);
                    // DESABILITAR BOTÃO
                    btn_salvar_orc.setEnabled(false);
                    btn_excluir_orc.setEnabled(true);

                    //LIMPANDO CAMPOS
                    entrada_nome_orc.setText(null);
                    entrada_telefone_orc.setText(null);
                    entrada_endereco_orc.setText(null);
                    jComboBox_situacao.setSelectedItem("ORÇAMENTO EM ABERTO");
                    jComboBox_ambiente.setSelectedItem("QUARTO");
                    entrada_desc_servico_orc.setText(null);
                    entrada_valor_material_orc.setText(null);
                    jComboBox_tipo_mdf_orc.setSelectedItem("BRANCO");
                    entrada_id_cli.setText(null);
                    entrada_valor_total_orc.setText(null);
                    entrada_qtn_diaTrabalado.setText(null);
                    entrada_qtn_chapa.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar...\nVerifique o código sql ou executeUpadate", "Erro", 1);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar no banco" + e, "Erro Sql", 1);
        }
    }

    private void atualizarOrcamento() {
        String sql = "UPDATE tb_orc set nome_empresa=?, telefone_cli=?, endereco_cli=?, situacao_orc=?, ambiente_orc=?, descricao_orc=?, valor_material_orc=?, tipo_mdf_orc=?, id_cli=?, valor_total_orc=?, qtn_dias=?, qtn_chapa=? where id_orc = ?";
        try {
            // preparando conexao
            pst = conexao.prepareStatement(sql);
            // Pegando variáveis

            pst.setString(1, entrada_nome_orc.getText());
            pst.setString(2, entrada_telefone_orc.getText());
            pst.setString(3, entrada_endereco_orc.getText());
            pst.setString(4, jComboBox_situacao.getSelectedItem().toString());
            pst.setString(5, jComboBox_ambiente.getSelectedItem().toString());
            pst.setString(6, entrada_desc_servico_orc.getText());
            pst.setString(7, entrada_valor_material_orc.getText());
            pst.setString(8, jComboBox_tipo_mdf_orc.getSelectedItem().toString());
            pst.setString(9, entrada_id_cli.getText());
            pst.setString(10, entrada_valor_total_orc.getText());
            pst.setString(11, entrada_qtn_diaTrabalado.getText());
            pst.setString(12, entrada_qtn_chapa.getText());
            pst.setString(13, entrada_id_orcamento.getText());

            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Orçamento Atualizado com Sucesso");
                //LIMPANDO CAMPOS
                entrada_nome_orc.setText(null);
                entrada_telefone_orc.setText(null);
                entrada_endereco_orc.setText(null);
                jComboBox_situacao.setSelectedItem("ORÇAMENTO EM ABERTO");
                jComboBox_ambiente.setSelectedItem("QUARTO");
                entrada_desc_servico_orc.setText(null);
                entrada_valor_material_orc.setText(null);
                jComboBox_tipo_mdf_orc.setSelectedItem("BRANCO");
                entrada_id_cli.setText(null);
                entrada_valor_total_orc.setText(null);
                entrada_qtn_diaTrabalado.setText(null);
                entrada_qtn_chapa.setText(null);
                entrada_pesquisa_orc.setText(null);
                id_identifacao.setText(null);

                // DESABILITANDO CAMPOS / BOTÕES
                btn_atualizar_orc.setEnabled(false);
                btn_editar_orc.setEnabled(false);
                btn_imprimir_orc.setEnabled(true);
                btn_excluir_orc.setEnabled(false);

                // BLOQUEAR ENTRADAS
                entrada_pesquisa_cliente.setEnabled(true);
                jComboBox_ambiente.setEnabled(false);
                jComboBox_situacao.setEnabled(false);
                jComboBox_tipo_mdf_orc.setEnabled(false);
                entrada_qtn_chapa.setEnabled(false);
                entrada_qtn_diaTrabalado.setEnabled(false);
                entrada_valor_material_orc.setEnabled(false);
                entrada_desc_servico_orc.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Verifique se nenhum campos está vazio\nVerificar erro de digitação\nVerificar com Suporte", "Erro", 2);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Verifique se nenhum campos está vazio\nVerificar erro de digitação\nVerificar com Suporte\n" + e, "Erro", 2);
        }

    }

    private void excluirOrc() {
        String sql = "DELETE FROM tb_orc where id_orc = ?";
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja exclur esse orçamento?", "Ecluir", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, entrada_id_orcamento.getText());

                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Orçamento apagado com sucesso", "Excluir", 2);
                    //LIMPANDO CAMPOS
                    entrada_nome_orc.setText(null);
                    entrada_telefone_orc.setText(null);
                    entrada_endereco_orc.setText(null);
                    jComboBox_situacao.setSelectedItem("ORÇAMENTO EM ABERTO");
                    jComboBox_ambiente.setSelectedItem("QUARTO");
                    entrada_desc_servico_orc.setText(null);
                    entrada_valor_material_orc.setText(null);
                    jComboBox_tipo_mdf_orc.setSelectedItem("BRANCO");
                    entrada_id_cli.setText(null);
                    entrada_valor_total_orc.setText(null);
                    entrada_qtn_diaTrabalado.setText(null);
                    entrada_qtn_chapa.setText(null);
                    entrada_pesquisa_orc.setText(null);
                    id_identifacao.setText(null);
                    entrada_id_orcamento.setText(null);

                    // BOTÃO
                    btn_excluir_orc.setEnabled(false);
                    entrada_pesquisa_cliente.setEnabled(true);
                    jTable_cliente_orc.setEnabled(true);
                    btn_calc_orc_salvar.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Verifique se a id esta correta\nou\nChame o Suporte", "Erro", 2);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao execultar a query\nChame o Suporte\n" + e, "Erro", 2);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_pesquisar_clientes = new javax.swing.JPanel();
        entrada_pesquisa_cliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_cliente_orc = new javax.swing.JTable();
        jPanel_indentificador = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        entrada_id_cli = new javax.swing.JTextField();
        entrada_id_orcamento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        jPanel_opções = new javax.swing.JPanel();
        btn_salvar_orc = new javax.swing.JButton();
        btn_atualizar_orc = new javax.swing.JButton();
        btn_excluir_orc = new javax.swing.JButton();
        jPanel_total = new javax.swing.JPanel();
        entrada_valor_total_orc = new javax.swing.JTextField();
        jPanel_dados = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        entrada_nome_orc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        entrada_telefone_orc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        entrada_endereco_orc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox_situacao = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox_ambiente = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        entrada_desc_servico_orc = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        entrada_valor_material_orc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox_tipo_mdf_orc = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        entrada_qtn_diaTrabalado = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        entrada_qtn_chapa = new javax.swing.JTextField();
        btn_calc_orc_salvar = new javax.swing.JButton();
        jPanelAtalho = new javax.swing.JPanel();
        btn_atalho_recibo = new javax.swing.JButton();
        btn_atalho_empresa = new javax.swing.JButton();
        btn_atalho_cliente = new javax.swing.JButton();
        btn_imprimir_orc = new javax.swing.JButton();
        entrada_pesquisa_orc = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_orc = new javax.swing.JTable();
        id_identifacao = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        btn_calc_orc = new javax.swing.JButton();
        btn_editar_orc = new javax.swing.JButton();
        btn_novo_orc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Orçamento");
        setResizable(false);

        jPanel_pesquisar_clientes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisar Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        entrada_pesquisa_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                entrada_pesquisa_clienteKeyReleased(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/procurar.png"))); // NOI18N

        jTable_cliente_orc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ));
        jTable_cliente_orc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_cliente_orcMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_cliente_orc);

        javax.swing.GroupLayout jPanel_pesquisar_clientesLayout = new javax.swing.GroupLayout(jPanel_pesquisar_clientes);
        jPanel_pesquisar_clientes.setLayout(jPanel_pesquisar_clientesLayout);
        jPanel_pesquisar_clientesLayout.setHorizontalGroup(
            jPanel_pesquisar_clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_pesquisar_clientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_pesquisar_clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(entrada_pesquisa_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1))
        );
        jPanel_pesquisar_clientesLayout.setVerticalGroup(
            jPanel_pesquisar_clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_pesquisar_clientesLayout.createSequentialGroup()
                .addGroup(jPanel_pesquisar_clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel_pesquisar_clientesLayout.createSequentialGroup()
                        .addComponent(entrada_pesquisa_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel_indentificador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Identificador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("ID CLIENTE");

        entrada_id_cli.setEnabled(false);

        entrada_id_orcamento.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("ID ORÇA.");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("DATA");

        txtData.setEnabled(false);

        javax.swing.GroupLayout jPanel_indentificadorLayout = new javax.swing.GroupLayout(jPanel_indentificador);
        jPanel_indentificador.setLayout(jPanel_indentificadorLayout);
        jPanel_indentificadorLayout.setHorizontalGroup(
            jPanel_indentificadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_indentificadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_indentificadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_indentificadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_indentificadorLayout.createSequentialGroup()
                        .addComponent(entrada_id_cli, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(entrada_id_orcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_indentificadorLayout.setVerticalGroup(
            jPanel_indentificadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_indentificadorLayout.createSequentialGroup()
                .addGroup(jPanel_indentificadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(entrada_id_cli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_indentificadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(entrada_id_orcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel_opções.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opções", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        btn_salvar_orc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/salvar_cli.png"))); // NOI18N
        btn_salvar_orc.setToolTipText("Salvar Orçamento");
        btn_salvar_orc.setEnabled(false);
        btn_salvar_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_orcActionPerformed(evt);
            }
        });

        btn_atualizar_orc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/atualizar_cli2.png"))); // NOI18N
        btn_atualizar_orc.setToolTipText("Atualizar");
        btn_atualizar_orc.setEnabled(false);
        btn_atualizar_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizar_orcActionPerformed(evt);
            }
        });

        btn_excluir_orc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/exclui_orca.png"))); // NOI18N
        btn_excluir_orc.setToolTipText("Excluir Orçamento");
        btn_excluir_orc.setEnabled(false);
        btn_excluir_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excluir_orcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_opçõesLayout = new javax.swing.GroupLayout(jPanel_opções);
        jPanel_opções.setLayout(jPanel_opçõesLayout);
        jPanel_opçõesLayout.setHorizontalGroup(
            jPanel_opçõesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_opçõesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_opçõesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_opçõesLayout.createSequentialGroup()
                        .addComponent(btn_salvar_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_atualizar_orc, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                    .addGroup(jPanel_opçõesLayout.createSequentialGroup()
                        .addComponent(btn_excluir_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel_opçõesLayout.setVerticalGroup(
            jPanel_opçõesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_opçõesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_opçõesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_salvar_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_atualizar_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_excluir_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel_total.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total (R$)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        entrada_valor_total_orc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        entrada_valor_total_orc.setEnabled(false);
        entrada_valor_total_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entrada_valor_total_orcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_totalLayout = new javax.swing.GroupLayout(jPanel_total);
        jPanel_total.setLayout(jPanel_totalLayout);
        jPanel_totalLayout.setHorizontalGroup(
            jPanel_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_totalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entrada_valor_total_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_totalLayout.setVerticalGroup(
            jPanel_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_totalLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(entrada_valor_total_orc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel_dados.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("NOME/EMPRESA");

        entrada_nome_orc.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("TELEFONE");

        entrada_telefone_orc.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("ENDEREÇO");

        entrada_endereco_orc.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("SITUAÇÃO");

        jComboBox_situacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ORÇAMENTO EM ABERTO", "ORÇAMENTO FECHADO", "ORÇAMENTO CONSULTA" }));
        jComboBox_situacao.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("AMBIENTE");

        jComboBox_ambiente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "QUARTO", "SALA", "COZINHA", "BANHEIRO", "ESCRTÓRIO", "CASA/APARTAMENTO INTEIRA", "LOJA" }));
        jComboBox_ambiente.setEnabled(false);

        entrada_desc_servico_orc.setColumns(20);
        entrada_desc_servico_orc.setRows(5);
        entrada_desc_servico_orc.setEnabled(false);
        jScrollPane2.setViewportView(entrada_desc_servico_orc);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("DESC/SERVIÇO");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("FERRAGEM E ADICIONAIS (R$)");

        entrada_valor_material_orc.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("TIPO MDF");

        jComboBox_tipo_mdf_orc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BRANCO", "MADEIRADO", "BRILHO" }));
        jComboBox_tipo_mdf_orc.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("QTN DIAS TRABALHADO");

        entrada_qtn_diaTrabalado.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("QTN");

        entrada_qtn_chapa.setEnabled(false);

        btn_calc_orc_salvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_calc_orc_salvar.setForeground(new java.awt.Color(51, 51, 255));
        btn_calc_orc_salvar.setText("Calcular Orçamento ");
        btn_calc_orc_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calc_orc_salvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_dadosLayout = new javax.swing.GroupLayout(jPanel_dados);
        jPanel_dados.setLayout(jPanel_dadosLayout);
        jPanel_dadosLayout.setHorizontalGroup(
            jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel_dadosLayout.createSequentialGroup()
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(entrada_nome_orc, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(entrada_telefone_orc, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(entrada_endereco_orc, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(jComboBox_situacao, 0, 401, Short.MAX_VALUE)
                    .addComponent(jComboBox_ambiente, 0, 401, Short.MAX_VALUE)
                    .addComponent(entrada_valor_material_orc, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(entrada_qtn_diaTrabalado)
                    .addGroup(jPanel_dadosLayout.createSequentialGroup()
                        .addComponent(jComboBox_tipo_mdf_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(entrada_qtn_chapa))))
            .addGroup(jPanel_dadosLayout.createSequentialGroup()
                .addComponent(btn_calc_orc_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );
        jPanel_dadosLayout.setVerticalGroup(
            jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_dadosLayout.createSequentialGroup()
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(entrada_nome_orc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(entrada_telefone_orc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(entrada_endereco_orc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox_situacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox_ambiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(3, 3, 3)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(entrada_valor_material_orc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_tipo_mdf_orc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(entrada_qtn_chapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(entrada_qtn_diaTrabalado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_calc_orc_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanelAtalho.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atalhos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        btn_atalho_recibo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_atalho_recibo.setText("FAZER RECIBO");
        btn_atalho_recibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atalho_reciboActionPerformed(evt);
            }
        });

        btn_atalho_empresa.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_atalho_empresa.setText("CONFIG. EMPRESA");
        btn_atalho_empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atalho_empresaActionPerformed(evt);
            }
        });

        btn_atalho_cliente.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_atalho_cliente.setText("CADASTRAR CLIENTE");
        btn_atalho_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atalho_clienteActionPerformed(evt);
            }
        });

        btn_imprimir_orc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/imprimir.png"))); // NOI18N
        btn_imprimir_orc.setToolTipText("Imprimir Orçamento");
        btn_imprimir_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimir_orcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAtalhoLayout = new javax.swing.GroupLayout(jPanelAtalho);
        jPanelAtalho.setLayout(jPanelAtalhoLayout);
        jPanelAtalhoLayout.setHorizontalGroup(
            jPanelAtalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtalhoLayout.createSequentialGroup()
                .addGroup(jPanelAtalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_atalho_cliente)
                    .addComponent(btn_atalho_recibo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAtalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_atalho_empresa, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_imprimir_orc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
        jPanelAtalhoLayout.setVerticalGroup(
            jPanelAtalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtalhoLayout.createSequentialGroup()
                .addGroup(jPanelAtalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_atalho_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_atalho_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAtalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_atalho_recibo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_imprimir_orc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        entrada_pesquisa_orc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        entrada_pesquisa_orc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                entrada_pesquisa_orcKeyReleased(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infinitysolucoes/icones/procurar.png"))); // NOI18N

        jTable_orc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ));
        jTable_orc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_orcMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_orc);

        id_identifacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        id_identifacao.setEnabled(false);
        id_identifacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_identifacaoActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("ID");

        jButton1.setText("ESCOLHER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("PROCURAR ORÇAMENTO ( EDITAR / ATUALZAR / EXCLUIR)");

        btn_calc_orc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_calc_orc.setForeground(new java.awt.Color(51, 51, 255));
        btn_calc_orc.setText("ReCalcular");
        btn_calc_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calc_orcActionPerformed(evt);
            }
        });

        btn_editar_orc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_editar_orc.setForeground(new java.awt.Color(51, 51, 255));
        btn_editar_orc.setText("Editar orçamento seleconado");
        btn_editar_orc.setEnabled(false);
        btn_editar_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_orcActionPerformed(evt);
            }
        });

        btn_novo_orc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_novo_orc.setForeground(new java.awt.Color(51, 51, 255));
        btn_novo_orc.setText("Novo Orçamento");
        btn_novo_orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_novo_orcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_pesquisar_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_identifacao))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_editar_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_calc_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_novo_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(entrada_pesquisa_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15))
                    .addComponent(jLabel17)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_indentificador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_opções, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelAtalho, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel_opções, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelAtalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel_indentificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(entrada_pesquisa_orc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(id_identifacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn_editar_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_calc_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_novo_orc, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel15)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_pesquisar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1217, 708));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void entrada_valor_total_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entrada_valor_total_orcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entrada_valor_total_orcActionPerformed

    private void entrada_pesquisa_clienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_pesquisa_clienteKeyReleased
        // Pesquisar cliente na tabela
        pesquisar_cliente();

    }//GEN-LAST:event_entrada_pesquisa_clienteKeyReleased

    private void jTable_cliente_orcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_cliente_orcMouseClicked
        // ESCOLHER CLIENTE DA TABELA
        setar_campos();
    }//GEN-LAST:event_jTable_cliente_orcMouseClicked

    private void btn_atalho_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atalho_clienteActionPerformed
        // ABRI TELA CADASTRO CLIENTE
        TelaCadCliente Tcliente = new TelaCadCliente();
        Tcliente.setVisible(true);
    }//GEN-LAST:event_btn_atalho_clienteActionPerformed

    private void btn_atalho_empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atalho_empresaActionPerformed
        // ABRIR TELA CONFIGURAÇÃO EMPRESA
        TelaCadEmpresa TcadEmpresa = new TelaCadEmpresa();
        TcadEmpresa.setVisible(true);
    }//GEN-LAST:event_btn_atalho_empresaActionPerformed

    private void btn_atalho_reciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atalho_reciboActionPerformed
        // ABRIR TELA RECIBO
        TelaRecibo Trecibo = new TelaRecibo();
        Trecibo.setVisible(true);
    }//GEN-LAST:event_btn_atalho_reciboActionPerformed

    private void btn_editar_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_orcActionPerformed
        EditarOrcamento();

    }//GEN-LAST:event_btn_editar_orcActionPerformed

    private void btn_salvar_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_orcActionPerformed
        // SALVAR ORÇAMENTO
        salvarOrcamento();
    }//GEN-LAST:event_btn_salvar_orcActionPerformed

    private void entrada_pesquisa_orcKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entrada_pesquisa_orcKeyReleased
        // TODO add your handling code here:
        pesquisar_orcamento();
    }//GEN-LAST:event_entrada_pesquisa_orcKeyReleased

    private void jTable_orcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_orcMouseClicked
        // ESCOLHER NA TABELA

        escolherTabela();
    }//GEN-LAST:event_jTable_orcMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // ESCOLHER ORÇAMENTO
        escolherOrcamento();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_calc_orc_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calc_orc_salvarActionPerformed
        // CALCULAR ANTES DE SALVAR
        calcularOrcamento();
    }//GEN-LAST:event_btn_calc_orc_salvarActionPerformed

    private void btn_imprimir_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimir_orcActionPerformed
        imprimirOrcamento();
        //Entrar na janela Imprimir oçamento
        //TelaImprimeOrc imprimir = new TelaImprimeOrc();
        //imprimir.setVisible(true);
    }//GEN-LAST:event_btn_imprimir_orcActionPerformed

    private void btn_calc_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calc_orcActionPerformed
        // 
        calcularOrcamentoNovo();
    }//GEN-LAST:event_btn_calc_orcActionPerformed

    private void btn_novo_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_novo_orcActionPerformed
        // NOVO ORCAMENTO
        novoOrcamento();
    }//GEN-LAST:event_btn_novo_orcActionPerformed

    private void btn_atualizar_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizar_orcActionPerformed
        // Atualizar Orçamento
        atualizarOrcamento();
    }//GEN-LAST:event_btn_atualizar_orcActionPerformed

    private void btn_excluir_orcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excluir_orcActionPerformed
        // Excluir Orçamento
        excluirOrc();
    }//GEN-LAST:event_btn_excluir_orcActionPerformed

    private void id_identifacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_identifacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_identifacaoActionPerformed

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
            java.util.logging.Logger.getLogger(TelaOrcamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaOrcamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaOrcamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaOrcamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaOrcamento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_atalho_cliente;
    private javax.swing.JButton btn_atalho_empresa;
    private javax.swing.JButton btn_atalho_recibo;
    private javax.swing.JButton btn_atualizar_orc;
    private javax.swing.JButton btn_calc_orc;
    private javax.swing.JButton btn_calc_orc_salvar;
    private javax.swing.JButton btn_editar_orc;
    private javax.swing.JButton btn_excluir_orc;
    private javax.swing.JButton btn_imprimir_orc;
    private javax.swing.JButton btn_novo_orc;
    private javax.swing.JButton btn_salvar_orc;
    public javax.swing.JTextArea entrada_desc_servico_orc;
    public javax.swing.JTextField entrada_endereco_orc;
    public javax.swing.JTextField entrada_id_cli;
    public static javax.swing.JTextField entrada_id_orcamento;
    public javax.swing.JTextField entrada_nome_orc;
    private javax.swing.JTextField entrada_pesquisa_cliente;
    private javax.swing.JTextField entrada_pesquisa_orc;
    public javax.swing.JTextField entrada_qtn_chapa;
    public javax.swing.JTextField entrada_qtn_diaTrabalado;
    public javax.swing.JTextField entrada_telefone_orc;
    public javax.swing.JTextField entrada_valor_material_orc;
    public javax.swing.JTextField entrada_valor_total_orc;
    private javax.swing.JTextField id_identifacao;
    private javax.swing.JButton jButton1;
    public javax.swing.JComboBox<String> jComboBox_ambiente;
    public javax.swing.JComboBox<String> jComboBox_situacao;
    public javax.swing.JComboBox<String> jComboBox_tipo_mdf_orc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelAtalho;
    private javax.swing.JPanel jPanel_dados;
    private javax.swing.JPanel jPanel_indentificador;
    private javax.swing.JPanel jPanel_opções;
    private javax.swing.JPanel jPanel_pesquisar_clientes;
    private javax.swing.JPanel jPanel_total;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable_cliente_orc;
    private javax.swing.JTable jTable_orc;
    private javax.swing.JTextField txtData;
    // End of variables declaration//GEN-END:variables

}
