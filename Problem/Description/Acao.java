package Problem.Description;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public class Acao
{
	private String nome;
	 LinkedList<Transicao> transicoes;
	private double custo;
	private double ActionProbabilityproDominio;
	private double Q;
	public Acao(String nome)
	{
		this.nome = nome;
		transicoes = new LinkedList<Transicao>();
	}
	
	public String getName()
	{
		return nome;
	}
	public int TransSize()
	{
		return transicoes.size();
	}
	public Estado temTransicaoEstado()
	{int t= transicoes.size();
		for (int i=0;i<t;i++)
		{
			return transicoes.get(t).getState();
		}
		return null;
	}
	public void removeTransicao(Transicao t)
	{int size=transicoes.size();
		for (int i=0;i<size;i++)

		{if (t.getState().getName().equals(transicoes.get(i).getState().getName()))
			 transicoes.remove(i);
			size=transicoes.size();
		}
	}
	
	public void setCost(double custo)
	{
		this.custo = custo;
	}
	
	public double getCost()
	{
		//return 10.0;
		return custo;
	}
	
	public void adicionaTransicao(Estado proxEstado, double proA, double proB)
	{
		Transicao t = new Transicao(proxEstado, proA, proB);
	
		transicoes.add(t);
	}
	public boolean temTransicao()
	{if (transicoes.size()>0)
		return true;
	else
		return false;
	}
	public boolean isNull() {
		return transicoes.isEmpty();
	}
	
	public LinkedList<Transicao> getTransitions()
	{
		return transicoes;
	}
	
	public int hashCode()
	{
		return nome.hashCode();
	}

	public double getQ() {
		return Q;
	}

	public void setQ(double q) {
		Q = q;
	}

	public double getActionProbabilityproDominio() {
		return ActionProbabilityproDominio;
	}

	public void setActionProbabilityproDominio(double actionProbabilityproDominio) {
		ActionProbabilityproDominio = actionProbabilityproDominio;
	}

}