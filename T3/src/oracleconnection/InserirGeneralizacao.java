package oracleconnection;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InserirGeneralizacao
{

    Connection con;
    String tabela;
    JComboBox fks;
    JFrame j;
    JPanel panelBaixo, panelTopo;
    JButton sair, inserir;
    JTextField fields[];
    JLabel labels[];

    public InserirGeneralizacao(Connection con, String tabela, JComboBox fks)
    {
        this.con = con;
        this.tabela = tabela;
        this.fks = fks;

    }

    public void layoutInsercao()
    {
        j = new JFrame("ICMC-USP - SCC0240 - Projeto 3");
        j.setSize(700, 600);
        j.setLayout(new BorderLayout());
        j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //definindo áreas de botões
        panelBaixo = new JPanel();
        j.add(panelBaixo, BorderLayout.SOUTH);
        panelTopo = new JPanel();
        j.add(panelTopo, BorderLayout.NORTH);
        panelTopo.setLayout(new GridLayout(3, 3));
        //criando os botões
        sair = new JButton("Fechar");
        inserir = new JButton("Inserir");

        //área de status
        panelBaixo.add(inserir);
        panelBaixo.add(sair);

        fields = new JTextField[2];
        labels = new JLabel[3];
        for (int i = 0; i < 3; i++)
        {
            labels[i] = new JLabel();
        }
        for (int i = 0; i < 2; i++)
        {
            fields[i] = new JTextField();
        }
        labels[0].setText("Profissao");
        labels[1].setText("Nome");
        labels[2].setText("Idade");
        if (tabela.toUpperCase().compareTo("PESSOA") == 0)
        {
            panelTopo.add(labels[0]);
            panelTopo.add(fks);
            panelTopo.add(labels[1]);
            panelTopo.add(fields[0]);
            panelTopo.add(labels[2]);
            panelTopo.add(fields[1]);
        } else if (tabela.toUpperCase().compareTo("PROFISSAO") == 0)
        {
            panelTopo.add(labels[0]);
            panelTopo.add(fields[0]);
        } else
        {
            panelTopo.add(labels[1]);
            panelTopo.add(fks);
            panelTopo.add(labels[2]);
            panelTopo.add(fields[0]);
        }
        eventosBotoes();
        j.pack();
        j.setVisible(true);
    }

    public void eventosBotoes()
    {

        sair.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent ae)
            {
                j.dispose();
            }

        });
        inserir.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent ae)
            {
                if (tabela.toUpperCase().compareTo("PROFISSAO") == 0)
                {
                    inserirDados(fields[0].getText(), null, null, tabela, con);
                } else if (tabela.toUpperCase().compareTo("PESSOA") == 0)
                {
                    inserirDados(fields[0].getText(), fks.getSelectedItem().toString(),
                            fields[1].getText(), tabela, con);
                } else
                {
                    inserirDados(fks.getSelectedItem().toString(), fields[0].getText(),
                            null, tabela, con);
                }
            }

        });
    }

    public void inserirDados(String op1, String op2, String op3, String tabela, Connection con)
    {
        String input;
        if (op2 == null && op3 == null) //inserindo na tabela profissao
        {
            input = "INSERT INTO " + tabela + "(PROFISSAO) VALUES ('" + op1.toUpperCase() + "')";
        } else if (op3 == null) //inserindo na tabela ator ou diretor
        {
            op1 = op1.replace("[", "");
            op1 = op1.replace("]", "");
            input = "INSERT INTO " + tabela + "(NOME,IDADE) VALUES ('" + op1.toUpperCase() + "'," + op2.toUpperCase()
                    + ")";
        } else //inserindo na tabela pessoa
        {
            op2 = op2.replace("[", "");
            op2 = op2.replace("]", "");
            input = "INSERT INTO " + tabela + "(NOME, PROFISSAO, IDADE) VALUES ('" + op1.toUpperCase() + "','" + op2.toUpperCase()
                    + "'," + op3.toUpperCase() + ")";
        }

        Statement instrucao;
        System.out.println(input);
        try
        {
            instrucao = con.createStatement();
            instrucao.executeUpdate(input);

        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + e.getMessage());
        }

    }
}
