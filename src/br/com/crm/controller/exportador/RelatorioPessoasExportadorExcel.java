package br.com.crm.controller.exportador;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import br.com.crm.model.entity.Pessoa;

/**
 * Exportador para EXCEL o Relatorio de Pessoa
 * @author Vitor
 * @since 16 mai 2016
 */
public class RelatorioPessoasExportadorExcel extends ExportadorExcel {

	private List<Pessoa> pessoas;
	
	public RelatorioPessoasExportadorExcel(List<Pessoa> abastecimentos) {
		this.pessoas = abastecimentos;
	}

	@Override
	public void addContentToWorkbook(HSSFSheet sheet, String[] reportTitles) {
		int rowIndex = 0;
		
		//1.adiciona o titulo do Relatorio
		rowIndex = addReportTitles(sheet, rowIndex, reportTitles);
		
		//2.adiciona o conteudo
		
		//linha em branco
		rowIndex = addWhiteLine(sheet, rowIndex);

		
		//cabecalho da tabela
		int columnIndex = 0;
		HSSFRow headerRow = sheet.createRow( rowIndex++ );
		addHeaderValue( headerRow.createCell( columnIndex++ ), "ID");
		addHeaderValue( headerRow.createCell( columnIndex++ ), "Nome completo");
		addHeaderValue( headerRow.createCell( columnIndex++ ), "Email Principal");
		addHeaderValue( headerRow.createCell( columnIndex++ ), "Cidade");
		addHeaderValue( headerRow.createCell( columnIndex++ ), "UF");
		addHeaderValue( headerRow.createCell( columnIndex++ ), "Data Nascimento");
		addHeaderValue( headerRow.createCell( columnIndex++ ), "Idade");
		addHeaderValue( headerRow.createCell( columnIndex++ ), "Maturidade");
		addHeaderValue( headerRow.createCell( columnIndex++ ), "Sexo");
		
		
		//conteudo da tabela
		for (Pessoa pessoaVar : pessoas) {
			columnIndex = 0;
			HSSFRow contentRow = sheet.createRow( rowIndex++ );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairId(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairNomeCompleto(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairEmailPrincipal(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairId(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairId(pessoaVar) );			
			addContentValue( contentRow.createCell( columnIndex++ ), extrairDataNascimento(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairId(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairMaturidade(pessoaVar) );
			
			addContentValue( contentRow.createCell( columnIndex++ ), extrairId(pessoaVar) );
		}
		
		columnIndex = 0;
		HSSFRow contentRow = sheet.createRow( rowIndex++ );		
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");		
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");	
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");	
		addHeaderValue( contentRow.createCell( columnIndex++ ), "");	
	}

	private String extrairMaturidade(Pessoa pessoaVar) {
		return null;
	}

	private String extrairDataNascimento(Pessoa pessoaVar) {
		return null;
	}

	private String extrairEmailPrincipal(Pessoa pessoaVar) {
		return null;
	}

	private String extrairNomeCompleto(Pessoa pessoaVar) {
		return null;
	}

	private String extrairId(Pessoa pessoaVar) {
		return null;
	}



}
