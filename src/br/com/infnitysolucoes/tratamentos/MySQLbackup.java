/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infnitysolucoes.tratamentos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author Usuario
 */
public class MySQLbackup {
    // Constantes da classe
    //C:\xampp\mysql\data
    private static final String VERSION = "4.0.3";
    private static final String SEPARATOR = File.separator;
    private static final String MYSQL_PATH = "C:" + SEPARATOR
            + "xampp" + SEPARATOR
            + "mysql" + SEPARATOR
            + "bin" + SEPARATOR;
            

    private static final String PRESENTATION = "==========================================================\n"
            + "  Backup do banco de dados MySQL - Versao " + VERSION + "\n"
            + "  Autor: Marcelo Carvalho Pinto da Cunha\n\n"
            + "  Desenvolvido em 07/09/2009\n\n"
            + "  MarcWare Software, 2009-2012\n"
            + "==========================================================\n\n";

    // Lista dos bancos de dados a serem "backupeados"; se desejar adicionar mais,
    // basta colocar o nome separado por espaços dos outros nomes
    private static final String DATABASES = "db_marc_orc";

    private List<String> dbList = new ArrayList<String>();

    public void MySQLBackup() {

        String command = MYSQL_PATH + "mysqldump.exe";

        String[] databases = DATABASES.split(" ");

        for (int i = 0; i < databases.length; i++) {
            dbList.add(databases[i]);
        }

        // Mostra apresentação
        System.out.println(PRESENTATION);

        //lblProgresso.setText("Iniciando backups...");

        // Contador
        int i = 1;
        // Tempo
        long time1, time2, time;

        // Início
        time1 = System.currentTimeMillis();

        for (String dbName : dbList) {

            ProcessBuilder pb = new ProcessBuilder(
                    command,
                    "--user=root",
                    "--password=Abc12345",
                    dbName,
                    "--result-file=" + "C:" + SEPARATOR + "Marceneiro" + SEPARATOR + "backup" + SEPARATOR + dbName + ".sql");
            // C:\Marceneiro\backup

            try {

                System.out.println(
                        "Backup do banco de dados (" + i + "): " + dbName + " ...");

                pb.start();

            } catch (Exception e) {

                e.printStackTrace();

            }

            i++;

        }

        // Fim
        time2 = System.currentTimeMillis();

        // Tempo total da operação
        time = time2 - time1;
        
        JOptionPane.showMessageDialog(null,"\nBackups realizados com sucesso.\nTempo total de processamento: " + time + " ms\n" );

        // Avisa do sucesso
        System.out.println("\nBackups realizados com sucesso.\n\n");

        System.out.println("Tempo total de processamento: " + time + " ms\n");

        System.out.println("Finalizando...");

        try {

            // Paralisa por 2 segundos
            Thread.sleep(2000);

        } catch (Exception e) {
        }

    }
}
