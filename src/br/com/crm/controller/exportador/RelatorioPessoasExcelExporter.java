package br.com.crm.controller.exportador;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import br.com.crm.model.entity.Pessoa;

/**
 * Exportador para EXCEL o Relatorio de Pessoa
 * @author Vitor
 * @since 16 mai 2016
 */
public class RelatorioPessoasExcelExporter extends ExcelExporter {

	private List<Pessoa> pessoas;
	
	public RelatorioPessoasExcelExporter(List<Pessoa> abastecimentos) {
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
			addContentValue( contentRow.createCell( columnIndex++ ), extrairCidade(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairUF(pessoaVar) );			
			addContentValue( contentRow.createCell( columnIndex++ ), extrairDataNascimento(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairIdade(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairMaturidade(pessoaVar) );
			addContentValue( contentRow.createCell( columnIndex++ ), extrairSexo(pessoaVar) );
		}
	}

	private String extrairSexo(Pessoa pessoaVar) {
		return pessoaVar.getGenero()!=null ? pessoaVar.getGenero().getDescricao() : "";
	}

	private Integer extrairIdade(Pessoa pessoaVar) {
		return pessoaVar.getIdadeCalculada();
	}

	private String extrairUF(Pessoa pessoaVar) {
		return pessoaVar.getEndereco().getEnderecoCidade();
	}

	private String extrairCidade(Pessoa pessoaVar) {
		return pessoaVar.getEndereco().getEnderecoCidade();
	}

	private String extrairMaturidade(Pessoa pessoa) {
		return pessoa.getMaturidade()!=null ? pessoa.getMaturidade().getDescricao() : "";
	}

	private Date extrairDataNascimento(Pessoa pessoa) {
		return pessoa.getDataNascimentoCompleta();
	}

	private String extrairEmailPrincipal(Pessoa pessoa) {
		return pessoa.getEmailPrincipal();
	}

	private String extrairNomeCompleto(Pessoa pessoa) {
		return pessoa.getNomeCompleto();
	}

	private Integer extrairId(Pessoa pessoa) {
		return pessoa.getId();
	}



}
