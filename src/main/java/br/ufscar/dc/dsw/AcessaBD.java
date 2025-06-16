package br.ufscar.dc.dsw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AcessaBD {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/BetwinVagas";
			Connection con = (Connection) DriverManager.getConnection(url,
					"root", "root");

			Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT V.titulo, V.descricao, V.remuneracao, V.data_limite_inscricao, V.cidade, E.nome AS nome_empresa " +
            "FROM Vagas V JOIN Empresas E ON V.id_empresa = E.id_empresa");
            
			 while (rs.next()) {
                System.out.println("Vaga: " + rs.getString("titulo"));
                System.out.println("  Descrição: " + rs.getString("descricao"));
                System.out.println("  Remuneração: " + rs.getDouble("remuneracao"));
                System.out.println("  Data Limite: " + rs.getDate("data_limite_inscricao"));
                System.out.println("  Cidade: " + rs.getString("cidade"));
                System.out.println("  Empresa: " + rs.getString("nome_empresa"));
                System.out.println("--------------------");
            }
            rs.close();
			stmt.close();
			con.close();
		} catch (ClassNotFoundException e) {
			System.out.println("A classe do driver de conexão não foi encontrada!");
		} catch (SQLException e) {
			System.out.println("O comando SQL não pode ser executado!");
		}
	}
}