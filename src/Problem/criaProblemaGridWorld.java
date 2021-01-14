package Problem;

import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Henri on 07/10/2019.
 */
public class criaProblemaGridWorld

{
    public Problem p;
    Estado m[][];
    int Nx = 0;
    int Ny = 0;
    Double ProbMoveInsideRiver;
    Double ProbMoveOutsideRiver;
    int xx[];
    int yy[];
    int metax[];
    int metay[];
    double costresto;
    double costmeta;
    double costparede;
int iny;
    int inx;
    public  criaProblemaGridWorld(int Nx, int Ny, Problem p, int[] x, int[] y, int[] metax, int[] metay, double p2, double costmeta, double costparede, double costresto,int inx,int iny,double ProbMoveOutsideRiver ) {
        this.Nx = Nx;
        this.Ny = Ny;
        this.p = p;
        xx = x;
        yy = y;
        this.metax = metax;
        this.metay = metay;
        int y1 = Nx;
        this.costresto=costresto;
        this.costparede=costparede;
        this.costmeta=costmeta;
        this.inx=inx;
        this.iny=iny;
        m = new Estado[Nx][Ny];
        this.ProbMoveOutsideRiver=ProbMoveOutsideRiver;

   /*     for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            System.out.println();
            int i1 = 0;

            for (int z = 0; z < Ny; z++) {



              //  i1 = i1 + 1;
                m[i][z] = new Estado("x" + i + "y" + z);
                System.out.print(m[i][z].getName() + "   ");
            }


        }*/
        for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            System.out.println();
            int i1 = 0;

            for (int o = 0; o < Ny; o++) {



                //i1 = i1 + 1;
                m[i][o] = new Estado("x" + i + "y" + o);
                System.out.print(m[i][o].getName() + "   ");
            }

          //  y1=y1-1;
        }
        criatudo();
    }

    void criatudo() {


        for (int i = 0; i < Nx; i++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
        {//mover ao N em qualquer lugar
            for (int j = 0; j < Ny; j++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
            {
                m[i][j].setUtilityValueLinha(0.0);
            }
        }

        for (int i = 0; i < Nx; i++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
        {//mover ao N em qualquer lugar
            for (int j = 0; j < Ny; j++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
            {
                for (int l = 0; l < xx.length; l++) {
                    if (m[i][j].getName().equals("x" + xx[l] + "y" + yy[l])) {
                        m[i][j].setUtilityValueLinha(1.0);
                    }
                }
                for (int l = 0; l < metax.length; l++) {
                    if (m[i][j].getName().equals("x" + metax[l] + "y" + metay[l])) {
                        m[i][j].setUtilityValueLinha(2.0);
                    }
                }
            }
        }


        for (int i = 0; i < Nx; i++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
    {//mover ao N em qualquer lugar
        for (int j = 0; j < Ny; j++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
        {
            if (i - 1 < 0) {//mover ao  sul
                Acao a = new Acao("move-S");
                //a.setActionProbability(ProbMoveOutsideRiver);
               // a2.setActionProbability(1 - ProbMoveOutsideRiver);
                //a.setCurrentState(m[i][j]);
                //a2.setCurrentState(m[i][j]);
                //a.setSuccessorState(m[i][j]);
                //a2.setSuccessorState(m[i][j]);
               // a.setCost(costresto);
                //a2.setCost(costresto);
                //a.setActionName("move-S");
                //a2.setActionName("move-S");
                a.adicionaTransicao(m[i][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                a.setCost(costresto);
                m[i][j].addAcao(a);             ;
            } else if (i > 0) {
                Acao a = new Acao("move-S");
             /*   ação a = new ação();
                ação a2 = new ação();
                a.setCost(costresto);
                a2.setCost(costresto);
                a.setActionProbability(ProbMoveOutsideRiver);
                a2.setActionProbability(1 - ProbMoveOutsideRiver);
                a.setCurrentState(m[i][j]);
                a2.setCurrentState(m[i][j]);*/
             a.setCost(costresto);
            //acho que nao precisa
             //    a.adicionaTransicao(m[i][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
           //     a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);

                //  m[i][j].addAcao(a);

                if (m[i - 1][j].getUtilityValueLinha() == 1.0)//barreira
                {
              /*      a.setCost(costparede);
                    a2.setCost(costparede);
                    a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1-ProbMoveOutsideRiver);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i-1][j]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-S");
                    a2.setActionName("move-S");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/

              a.setCost(costresto);
              a.adicionaTransicao(m[i-1][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
              a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
              m[i][j].addAcao(a);

                } else if (m[i - 1][j].getUtilityValueLinha() == 2.0)//meta
                {
                 /*   a.setCost(costmeta);
                    a2.setCost(costmeta);
                    a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1 - ProbMoveOutsideRiver);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i - 1][j]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-S");
                    a2.setActionName("move-S");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);
*/
                    a.setCost(costmeta);
                    a.adicionaTransicao(m[i-1][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                } else {
                 /*   a.setCost(costresto);
                    a2.setCost(costresto);
                    a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1 - ProbMoveOutsideRiver);
                    a.setSuccessorState(m[i - 1][j]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-S");
                    a2.setActionName("move-S");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/
                    a.setCost(costresto);
                    a.adicionaTransicao(m[i-1][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                }

            }

            if (j - 1 < 0) {//mover a  oeste
             /*   ação a = new ação();
                ação a2 = new ação();
                a.setCost(costresto);
                a2.setCost(costresto);
                a.setActionProbability(ProbMoveOutsideRiver);
                a2.setActionProbability(1 - ProbMoveOutsideRiver);
                a.setCurrentState(m[i][j]);
                a2.setCurrentState(m[i][j]);
                a.setSuccessorState(m[i][j]);
                a2.setSuccessorState(m[i][j]);
                a.setActionName("move-W");
                a2.setActionName("move-W");
                m[i][j].addAction(a);
                m[i][j].addAction(a2);
                //     m[i][j].addAction(a2);*/
             Acao a = new Acao("move-W");
                a.adicionaTransicao(m[i][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                a.setCost(costresto);
                m[i][j].addAcao(a);

            } else if (j > 0) {
               /* ação a = new ação();
                ação a2 = new ação();
                a.setCost(costresto);
                a2.setCost(costresto);
                a.setActionProbability(ProbMoveOutsideRiver);
                a2.setActionProbability(1 - ProbMoveOutsideRiver);*/
                Acao a = new Acao("move-W");
                if (m[i][j - 1].getUtilityValueLinha() == 1.0)//barreira
                {
                   /* a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1 - ProbMoveOutsideRiver);
                    a.setCost(costparede);
                    a2.setCost(costparede);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i][j-1]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-W");
                    a2.setActionName("move-W");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/

                    a.setCost(costparede);
                    a.adicionaTransicao(m[i][j-1],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                } else if (m[i][j - 1].getUtilityValueLinha() == 2.0)//meta
                {
                    /*a.setCost(costmeta);
                    a2.setCost(costmeta);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i][j - 1]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-W");
                    a2.setActionName("move-W");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/

                    a.setCost(costmeta);
                    a.adicionaTransicao(m[i][j-1],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                } else {

                   /* a.setCost(c);
                    a2.setCost(costresto);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i][j - 1]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-W");
                    a2.setActionName("move-W");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/
                    a.setCost(costresto);
                    a.adicionaTransicao(m[i][j-1],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                }
            }

            if (i + 1 >= Nx) {//mover ao norte
               /* ação a = new ação();
                ação a2 = new ação();
                a.setCost(costresto);
                a2.setCost(costresto);
                a.setActionProbability(ProbMoveOutsideRiver);
                a2.setActionProbability(1 - ProbMoveOutsideRiver);
                a.setCurrentState(m[i][j]);
                a2.setCurrentState(m[i][j]);
                a.setSuccessorState(m[i][j]);
                a2.setSuccessorState(m[i][j]);
                a.setActionName("move-N");
                a2.setActionName("move-N");
                m[i][j].addAction(a);
                m[i][j].addAction(a2);
                //     m[i][j].addAction(a2);*/
                Acao a = new Acao("move-N");
                a.adicionaTransicao(m[i][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                a.setCost(costresto);
                m[i][j].addAcao(a);

            } else if (i < Nx) {
              /*  ação a = new ação();
                ação a2 = new ação();
                a.setActionProbability(ProbMoveOutsideRiver);
                a2.setActionProbability(1 - ProbMoveOutsideRiver);
                a.setCurrentState(m[i][j]);
                a2.setCurrentState(m[i][j]);
                a.setSuccessorState(m[i + 1][j]);
                a2.setSuccessorState(m[i][j]);
                a.setCost(costresto);
                a2.setCost(costresto);
                //  a.setActionName("move-N");
                //   a2.setActionName("move-N");
                //  m[i][j].addAction(a);
                //  m[i][j].addAction(a2);*/
                Acao a = new Acao("move-N");
                if (m[i + 1][j].getUtilityValueLinha() == 1.0)//barreira
                {
                   /* a.setCost(costparede);
                    a2.setCost(costparede);
                    a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1-ProbMoveOutsideRiver);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i+1][j]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-N");
                    a2.setActionName("move-N");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/
                    a.setCost(costparede);
                    a.adicionaTransicao(m[i+1][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                } else if (m[i + 1][j].getUtilityValueLinha() == 2.0)//meta
                {
                   /* a.setCost(costmeta);
                    a2.setCost(costmeta);
                    a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1 - ProbMoveOutsideRiver);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i + 1][j]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-N");
                    a2.setActionName("move-N");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/
                    a.setCost(costmeta);
                    a.adicionaTransicao(m[i+1][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                } else {/*
                    a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1 - ProbMoveOutsideRiver);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i + 1][j]);
                    a2.setSuccessorState(m[i][j]);
                    a.setCost(costresto);
                    a2.setCost(costresto);
                    a.setActionName("move-N");
                    a2.setActionName("move-N");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);
*/
                    a.setCost(costresto);
                    a.adicionaTransicao(m[i+1][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);

                }
            }
            if (j + 1 >= Ny) {//mover a direita
               /* ação a = new ação();
                ação a2 = new ação();
                a.setCost(costresto);
                a2.setCost(costresto);

                a.setActionProbability(ProbMoveOutsideRiver);
                a2.setActionProbability(1 - ProbMoveOutsideRiver);
                a.setCurrentState(m[i][j]);
                a2.setCurrentState(m[i][j]);
                a.setSuccessorState(m[i][j]);
                a2.setSuccessorState(m[i][j]);
                a.setActionName("move-E");
                a2.setActionName("move-E");
                m[i][j].addAction(a);
                m[i][j].addAction(a2);
*/
                Acao a = new Acao("move-E");
                a.adicionaTransicao(m[i][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                a.setCost(costresto);
                m[i][j].addAcao(a);
            } else if (j < Ny) {
               /* ação a = new ação();
                ação a2 = new ação();
                a.setActionProbability(ProbMoveOutsideRiver);
                a2.setActionProbability(1 - ProbMoveOutsideRiver);
                a.setCost(costresto);
                a2.setCost(costresto);

                a.setCurrentState(m[i][j]);
                a2.setCurrentState(m[i][j]);
                a.setSuccessorState(m[i][j + 1]);
                a2.setSuccessorState(m[i][j]);

                a.setActionName("move-E");
                a2.setActionName("move-E");*/
                Acao a = new Acao("move-E");
                if (m[i][j + 1].getUtilityValueLinha() == 1.0)//barreira
                {
                   /* a.setCost(costparede);
                    a2.setCost(costparede);
                    a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1-ProbMoveOutsideRiver);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i][j+1]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-E");
                    a2.setActionName("move-E");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/

                    a.setCost(costparede);
                    a.adicionaTransicao(m[i][j+1],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);


                } else if (m[i][j + 1].getUtilityValueLinha() == 2.0)//meta
                {
                   /* a.setCost(costmeta);
                    a2.setCost(costmeta);
                    a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1 - ProbMoveOutsideRiver);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i][j + 1]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-E");
                    a2.setActionName("move-E");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/
                    a.setCost(costmeta);
                    a.adicionaTransicao(m[i][j+1],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                } else {
                 /*   a.setActionProbability(ProbMoveOutsideRiver);
                    a2.setActionProbability(1 - ProbMoveOutsideRiver);
                    a.setCost(costresto);
                    a2.setCost(costresto);

                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i][j + 1]);
                    a2.setSuccessorState(m[i][j]);

                    a.setActionName("move-E");
                    a2.setActionName("move-E");
                    m[i][j].addAction(a);
                    m[i][j].addAction(a2);*/
                    a.setCost(costresto);
                    a.adicionaTransicao(m[i][j+1],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                    a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                    m[i][j].addAcao(a);
                }
            }


        }
    }


        for (int i = 0; i < xx.length; i++) {
  /*  m[xx[i]][yy[i]].getActions().clear();
     a = new ação();
    ação a2 = new ação();
    ação a3 = new ação();
    ação a4 = new ação();
    a.setActionProbability(0);
    a2.setActionProbability(0);
    a3.setActionProbability(0);
    a4.setActionProbability(0);
    a.setCurrentState(m[xx[i]][yy[i]]);
    a2.setCurrentState((m[xx[i]][yy[i]]));
    a3.setSuccessorState((m[xx[i]][yy[i]]));
    a4.setSuccessorState((m[xx[i]][yy[i]]));
    a.setSuccessorState((m[xx[i]][yy[i]]));
    a2.setSuccessorState((m[xx[i]][yy[i]]));
    a3.setSuccessorState((m[xx[i]][yy[i]]));
    a4.setSuccessorState((m[xx[i]][yy[i]]));
    a.setActionName("move-N");
    a2.setActionName("move-S");
    a3.setActionName("move-E");
    a4.setActionName("move-W");
    m[xx[i]][yy[i]].addAction(a);
    m[xx[i]][yy[i]].addAction(a2);
    m[xx[i]][yy[i]].addAction(a3);
    m[xx[i]][yy[i]].addAction(a4);
*/
            m[xx[i]][yy[i]].apagaMeta();
            Acao a = new Acao("move-N");
            Acao a1 = new Acao("move-S");
            Acao a2 = new Acao("move-E");
            Acao a3 = new Acao("move-W");
            a.setCost(costparede);
            a2.setCost(costparede);
            a3.setCost(costparede);
            a1.setCost(costparede);
            a.adicionaTransicao( m[xx[i]][yy[i]],1,0);
            a1.adicionaTransicao( m[xx[i]][yy[i]],1,0);
            a2.adicionaTransicao( m[xx[i]][yy[i]],1,0);
            a3.adicionaTransicao( m[xx[i]][yy[i]],1,0);
            m[xx[i]][yy[i]].addAcao(a);
            m[xx[i]][yy[i]].addAcao(a1);
            m[xx[i]][yy[i]].addAcao(a2);
            m[xx[i]][yy[i]].addAcao(a3);



}
        for (int i = 0; i < metax.length; i++) {
  /*  m[metax[i]][metay[i]].getActions().clear();

    ação a = new ação();
    ação a2 = new ação();
    ação a3 = new ação();
    ação a4 = new ação();
    a.setActionProbability(0);
    a2.setActionProbability(0);
    a3.setActionProbability(0);
    a4.setActionProbability(0);
    a.setCurrentState(m[metax[i]][metay[i]]);
    a2.setCurrentState((m[metax[i]][metay[i]]));
    a3.setSuccessorState((m[metax[i]][metay[i]]));
    a4.setSuccessorState((m[metax[i]][metay[i]]));
    a.setSuccessorState((m[metax[i]][metay[i]]));
    a2.setSuccessorState((m[metax[i]][metay[i]]));
    a3.setSuccessorState((m[metax[i]][metay[i]]));
    a4.setSuccessorState((m[metax[i]][metay[i]]));
    a.setActionName("move-N");
    a2.setActionName("move-S");
    a3.setActionName("move-E");
    a4.setActionName("move-W");
    m[metax[i]][metay[i]].addAction(a);
    m[metax[i]][metay[i]].addAction(a2);
    m[metax[i]][metay[i]].addAction(a3);
    m[metax[i]][metay[i]].addAction(a4);
    r.setGoalState(m[metax[i]][metay[i]]);
    */
            m[metax[i]][metay[i]].apagaMeta();
            Acao a = new Acao("move-N");
            Acao a1 = new Acao("move-S");
            Acao a2 = new Acao("move-E");
            Acao a3 = new Acao("move-W");
            a.adicionaTransicao( m[metax[i]][metay[i]],0,0);
            a1.adicionaTransicao( m[metax[i]][metay[i]],0,0);
            a2.adicionaTransicao( m[metax[i]][metay[i]],0,0);
            a3.adicionaTransicao( m[metax[i]][metay[i]],0,0);
            m[metax[i]][metay[i]].addAcao(a);
            m[metax[i]][metay[i]].addAcao(a1);
            m[metax[i]][metay[i]].addAcao(a2);
            m[metax[i]][metay[i]].addAcao(a3);
            m[metax[i]][metay[i]].setGoal();
//            p.setFinalState(m[metax[i]][metay[i]].getName());


}

        Map<Integer, Estado> estados = new HashMap<Integer, Estado>();
        int j=0;
        for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            System.out.println();
            for (int y = 0; y < Ny; y++) {
                //m[i][y].setUtilityValueLinha(0.0);
                //  if(m[i][y].getName().equals(m[0][0].getName())==false||m[i][y].getName().equals(m[0][Ny-1].getName())==false)
                int l =m[i][y].getName().hashCode();

                estados.put(m[i][y].getName().trim().hashCode(), m[i][y]);
                j++;
            }


        }
        p.adicionaEstados(estados);
        p.setInitialState(m[inx][iny].getName());
        for (int i=0;i<metax.length;i++) {
            p.setFinalState(m[metax[i]][metay[i]].getName());
        }



     /*   for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
    {
        System.out.println();
        for (int y = 0; y < Ny; y++) {
            //m[i][y].setUtilityValueLinha(0.0);
            r.setState(m[i][y]);
        }


    }*/
}

}

