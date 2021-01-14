package Problem.Description;

import java.util.*;

public class Estado {
	private static final int QTD_ACAO = 11;

	private String nome;
	private int x;
	private int y;
	private int vX;
	private int s;
	private int i;
	private int r;
	private int vY;
	private Acao[] acoes;
	private int acaoIndex;
	private boolean goal;
	double UtilityValueLinha = 0;
	private Set<Estado> pai=new HashSet<>();
	private double custo;
	private boolean expandido;
	// Melhor acao
	private int bestEdge;
	int identificadorEstado;

	public Estado(String nome) {
		this.nome = nome;
		String[] nomes = nome.split("[x y v]");
		if (nomes.length > 3) nomes[3] = nomes[3].substring(1, nomes[3].length());
		if (nomes.length > 3) nomes[4] = nomes[4].substring(1, nomes[4].length());
		x = Integer.parseInt(nomes[1]);
		y = Integer.parseInt(nomes[2]);
		if (nomes.length > 3) vX = Integer.parseInt(nomes[3]);
		else vX = 1;
		if (nomes.length > 3) vY = Integer.parseInt(nomes[4]);
		else vY = 1;

		acoes = new Acao[QTD_ACAO];
		acaoIndex = 0;
		custo = 0;
		bestEdge = -1;
		expandido = false;
		goal = false;
	}
	public Estado(String nome,int faznada) {
		this.nome = nome;
		acoes = new Acao[QTD_ACAO];
		acaoIndex = 0;
		custo = 0;
		bestEdge = -1;
		expandido = false;
		goal = false;
	}

	public void setUtilityValueLinha(double a) {
		UtilityValueLinha = a;
	}

	public double getUtilityValueLinha() {
		return UtilityValueLinha;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVX() {
		return vX;
	}

	public int getVY() {
		return vY;
	}

	public int actionSize() {
		return acaoIndex;
	}

	public void addAcao(Acao a) {
		acoes[acaoIndex++] = a;
	}

	public Acao getAction(int i) {
		return acoes[i];
	}
	public Acao getActionPorNome(String t) {
		for (int i = 0; i < acoes.length; i++) {
			if (acoes[i].getName().equals(t)) {
				return acoes[i];
			}

		}
			return null;

	}

	public Acao[] getActions() {
		return acoes;
	}
	public void setCost(double cost) {
		custo = cost;
	}

	public double getCost() {
		return custo;
	}

	public boolean wasExpanded() {
		return expandido;
	}

	public void setExpanded() {
		expandido = true;
	}
	public void desesExpanded() {
		expandido = false;
	}

	public void setBestEdge(int bestEdge) {
		this.bestEdge = bestEdge;
	}

	public int getBestAction() {
		return bestEdge;
	}

	public String getName() {
		return nome;
	}

	public void setActionCost(int i, double cost) {
		acoes[i].setCost(cost);
	}

	public void apagaMeta() {
		acaoIndex = 0;
		acoes = new Acao[4];
	}

	public void setGoal() {
		goal = true;
		expandido = true;
	}

	public boolean isGoal() {
		return goal;
	}

	public int hashCode() {
		return nome.hashCode();
	}


	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public boolean verificaacao(String nome) {
		for (int i = 0; i < acoes.length; i++) {
			if (acoes[i].getName().equals(nome)) {
				return true;
			} else {
			//	return false;
			}
		}
		return false;
	}
public Set<Estado> getEstadoPai()
{
	return pai;
}
	public void setEstadoPai(Estado s)
	{
		pai.add(s);
	}


}