package br.com.ConexaoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.QueryExecutor;
import br.com.sankhya.extensions.actionbutton.Registro;

public class Conexao implements AcaoRotinaJava {
	public String registro;

	public static String status = "N�o conectou...";
	 	
	public static void main(String[] args) throws SQLException {
		System.out.println(RetornaStatus());
		Connection a = Conexao.getConexao();		
		//busca(a,"413122596341");
		//atualizar(a, "239");
		System.out.println(RetornaStatus());
		FecharConexao();
		System.out.println(RetornaStatus());
	}
	
	//M�todo de Conex�o//	 
	public static java.sql.Connection getConexao() {	 
	        	Connection connection = null;          //atributo do tipo Connection	 
	        try {	 
	        	// Carregando o JDBC Driver padr�o	 
	        	String driverName = "";                        	 
	        	Class.forName(driverName);
	        	// Configurando a nossa conex�o com um banco de dados//
	            String serverName = "";//"";    //caminho do servidor do BD	 
	            String mydatabase = "";//"";        //nome do seu banco de dados	 
	            String url = "jdbc:mysql://localhost:3306/"+ mydatabase + "?useSSL=true";
	            String username = "root";//"";        //nome de um usu�rio de seu BD      	 
	            String password = "";//"";      //sua senha de acesso
	 
	            connection = DriverManager.getConnection(url, username, password);
	 
	            //Testa sua conex�o//  	 
	            if (connection != null) {	 
	                status = ("STATUS--->Conectado com sucesso!");	
	            } else {	
	                status = ("STATUS--->N�o foi possivel realizar conex�o");	 
	            }
	          
	            return connection;
	        } catch (ClassNotFoundException e) {  //Driver n�o encontrado
	     System.out.println("O driver expecificado nao foi encontrado.");	 
	     	return null;
	 } catch (SQLException e) {	 
	//N�o conseguindo se conectar ao banco	 
	            System.out.println("Nao foi possivel conectar ao Banco de Dados.");	 
	            return null;	 
	        }
	 
	    }
	
	 //M�todo que retorna o status da sua conex�o//
    public static String statusConection() {
         return status;
    }
    
    public static void retornaStatus() {
    	System.out.println(status);
   }
    
    public static String RetornaStatus() {
    	return status;
   }
 
    //M�todo que fecha conex�o
    public static boolean FecharConexao() {
        try {
            Conexao.getConexao().close();
            status = ("STATUS--->Conex�o fechada");
            return true;
        } catch (SQLException e) {
        	status = ("STATUS--->Erro ao exibir status");
            return false;
        }
    }
 
   //M�todo que reinicia conex�o 
    public static java.sql.Connection ReiniciarConexao() {
        FecharConexao();

        return Conexao.getConexao(); 
    }
   
    //Busca no banco
	public static String busca(Connection conec,String busca) throws SQLException {
		java.sql.Statement acesso = conec.createStatement();
		
		try{  
			String sql = "SELECT * FROM pw_licenca where serial = '" + busca + "'";
			ResultSet res = acesso.executeQuery(sql);
			if(res != null && res.next()){
                status = res.getString("idLicenca");
            }
            System.out.println(status);  
        }  
        catch(SQLException ex){  
            ex.printStackTrace();  
        }  
	return status;
	}
	
	//Update no banco
	public static String atualizar (Connection conec,String busca) throws SQLException{
		java.sql.Statement acesso = conec.createStatement();		
		try{  
			String sql = "UPDATE pw_licenca SET numeroContrato = '789' where idLicenca = '" + busca + "'";			
			int res = acesso.executeUpdate(sql);
			if(res !=  0){
                status = "OK";
            }
            System.out.println(status);  
        }  
        catch(SQLException ex){  
            ex.printStackTrace();  
        }  
	return status;
		
	}
	@Override
	public void doAction(ContextoAcao contexto) throws Exception {
		Connection a = Conexao.getConexao();			
		//RECEBE DO SANKHYA	
		String teste = contexto.getParam("BUSC").toString();
		//BUSCA
		//String aa = busca(a,teste);
		//UPDATE
		String aa = atualizar(a,teste);
		
	contexto.setMensagemRetorno(aa);
	}

}
