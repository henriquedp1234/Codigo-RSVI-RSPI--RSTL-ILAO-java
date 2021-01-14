package Problem;

import Problem.Description.Acao;
import Problem.Description.Estado;
import Problem.Description.Problem;
import Problem.Description.Transicao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Henri on 13/10/2018.
 */
public class criaProblemaIgorVersãoVoltaInicio2 {//gera identico ao problema do texto mas custo negativo

    private Arquivo arquivo;

    private int qtdAcoes;
    public Problem p;

    Estado m[][];
    int Nx = 0;
    int Ny = 0;
    double costriver;

    double cost;
    double ProbMoveInsideRiver;
    double ProbMoveOutsideRiver;
    //int st = Nx * Ny;
    //RSMDP r;

    public criaProblemaIgorVersãoVoltaInicio2(int Nx, int Ny, double p, Problem prob, double costriver, double cost, double probMoveOutsideRiver) {
        // double p1 = p;
        ProbMoveInsideRiver = p;
        //ProbMoveOutsideRiver = 0.99;
        m = new Estado[Nx][Ny];
        this.Nx = Nx;
        this.Ny = Ny;
        this.p = prob;
        this.costriver=costriver;
        this.ProbMoveOutsideRiver=probMoveOutsideRiver;

        this.cost=cost;

        int y1 = Nx;
        // int i1 = 1;

        for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            System.out.println();
            int i1 = 0;

            for (int y = 0; y < Ny; y++) {



                i1 = i1 + 1;
                m[i][y] = new Estado("x" + i1 + "y" + y1);
                System.out.print(m[i][y].getName() + "   ");
            }

            y1=y1-1;
        }
        criatudo();
    }

    void criatudo() {
        for (int i = 1; i < Ny - 1; i++)//M.RiverMap(1,1+1:M.Ny-1)=3; %bridge
        {
            m[Nx - 1][i].setUtilityValueLinha(3.0);
        }
        for (int i = 1; i < Nx - 1; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            for (int y = 1; y < Ny - 1; y++) {
                m[i][y].setUtilityValueLinha(2.0);
            }
        }
        for (int i = 0; i < Nx; i++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
        {
            m[i][0].setUtilityValueLinha(1.0);
        }
        for (int i = 0; i < Nx; i++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
        {
            m[i][Ny - 1].setUtilityValueLinha(1.0);
        }
        for (int i = 1; i < Ny - 1; i++)// M.RiverMap(M.Ny, 1 + 1:M.Ny - 1)=-3; %waterfall
        {
            m[0][i].setUtilityValueLinha(-3.0);
        }

        for (int i = 0; i < Nx; i++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
        {
            m[i][Ny - 1].setUtilityValueLinha(1.0);
        }
        double prob[]=new double[Ny];

        for (int i=1;i<Ny-1;i++)
        {
            double i1=i;
            double ny1=Ny-1;
            double ç=(double)(i1/ny1);
            prob[(Ny-1)-i]=ç;
        }


        ////////////////////////////////////////////////////
        for (int i = 0; i < Nx; i++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
        {//mover ao N em qualquer lugar
            for (int j = 0; j < Ny; j++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
            {
//&& j + 1 < Ny
                if (i + 1 < Nx && (m[i + 1][j].getUtilityValueLinha() == 1 || m[i + 1][j].getUtilityValueLinha() == 3 || m[i + 1][j].getUtilityValueLinha() == -3 || m[i + 1][j].getUtilityValueLinha() == 2))//todas acoes para cima
                {

                    Acao a = new Acao("move-N");
                    a.setCost(cost);
                    //a.setCost(m[i + 1][j].getUtilityValueLinha().floatValue());
                    //a2.setCost(m[i + 1][j].getUtilityValueLinha().floatValue());



                    if (m[i][j].getUtilityValueLinha() == -3) {
                        a.adicionaTransicao(m[0][0],1,0);
                        a.adicionaTransicao(m[0][0],0,1);
                        m[i][j].addAcao(a);
                        //   a.setCurrentState(m[i][j]);
                        //  a2.setCurrentState(m[i][j]);
                        // a.setSuccessorState(m[0][0]);
                        //a2.setSuccessorState(m[0][0]);
                        //a.setActionProbability(1);
                        //a2.setActionProbability(0);
                        // m[i][j].addAction(a);
                        //   m[i][j].addAction(a2);


                    } else {
                        /*a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);

                        a.setSuccessorState(m[i + 1][j]);
                        a2.setSuccessorState(m[i][j]);*/

                        if (m[i][j].getUtilityValueLinha() == 2) {
                            //a.setActionProbability(1-ProbMoveInsideRiver);Math.round(ac.getActionProbability()*100)/100.0d
                            //a.setSuccessorState(m[i+1][j]);
                            // a2.setSuccessorState(m[i-1][j]);
                            // a2.setActionProbability((ProbMoveInsideRiver));
                            a.setCost(costriver);
                            //a.adicionaTransicao(m[i+1][j],1-ProbMoveInsideRiver,ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i+1][j],1-prob[i],prob[i]);
                            //a.adicionaTransicao(m[0][0],ProbMoveInsideRiver,1-ProbMoveInsideRiver);
                            a.adicionaTransicao(m[0][0],prob[i],prob[i]);
                            m[i][j].addAcao(a);


                        } else if (m[i][j].getUtilityValueLinha() == 1) {
                            // a.setActionProbability(ProbMoveOutsideRiver);
                            // a2.setActionProbability((1 - ProbMoveOutsideRiver));
                            a.adicionaTransicao(m[i+1][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                            a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                            m[i][j].addAcao(a);
                        } else if (m[i][j].getUtilityValueLinha() == 3) {
                            //a.setActionProbability(ProbMoveOutsideRiver);
                            //a2.setActionProbability((1 - ProbMoveOutsideRiver));
                            a.adicionaTransicao(m[i+1][j],1-prob[Ny-1],1-ProbMoveOutsideRiver);
                            a.adicionaTransicao(m[0][0],prob[Ny-1],ProbMoveOutsideRiver);
                            m[i][j].addAcao(a);
                        } else if (m[i][j].getUtilityValueLinha() == -3) {
                            //a.setActionProbability(ProbMoveInsideRiver);
                            //a2.setActionProbability((1 - ProbMoveInsideRiver));
                            a.adicionaTransicao(m[i+1][j],ProbMoveInsideRiver,1-ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j],1-ProbMoveInsideRiver,ProbMoveInsideRiver);
                            m[i][j].addAcao(a);
                        }
                        else if (m[i][j].getUtilityValueLinha() == -2) {
                            a.adicionaTransicao(m[i][j + 1], ProbMoveInsideRiver, 1 - ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j], 1 - ProbMoveInsideRiver, ProbMoveInsideRiver);
                            m[i][j].addAcao(a);
                        }

                    }
                }
                //i + 1 < Nx&&
                if (j + 1 < Ny && (m[i][j + 1].getUtilityValueLinha() == 1 || m[i][j + 1].getUtilityValueLinha() == 3 || m[i][j + 1].getUtilityValueLinha() == -3 || m[i][j + 1].getUtilityValueLinha() == 2))//todas acoes para cima
                {//mover  leste em qualquer lugar
                    Acao a = new Acao("move-E");
                    a.setCost(cost);
                    //a.setCost(m[i][j + 1].getUtilityValueLinha().floatValue());
                    //a2.setCost(m[i][j + 1].getUtilityValueLinha().floatValue());

                    // a2.setCost(0);
                    // a.setActionName("move-E");
                    // a2.setActionName("move-E");
                    if (m[i][j].getUtilityValueLinha() == -3) {
                      /*  a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[0][0]);
                        a2.setSuccessorState(m[0][0]);
                        a.setActionProbability(1);
                        a2.setActionProbability(0);
                        m[i][j].addAction(a);*/
                        a.adicionaTransicao(m[0][0],1,0);
                        a.adicionaTransicao(m[0][0],0,1);
                        m[i][j].addAcao(a);

                        //    m[i][j].addAction(a2);

                    } else {
                       /* a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);

                        a.setSuccessorState(m[i][j + 1]);
                        a2.setSuccessorState(m[i][j]);*/

                        if (m[i][j].getUtilityValueLinha() == 2) {
                            a.setCost(costriver);
                         /*   a.adicionaTransicao(m[i][j+1],1-ProbMoveInsideRiver,ProbMoveInsideRiver);//mudado
                            a.adicionaTransicao(m[0][0],ProbMoveInsideRiver,1-ProbMoveInsideRiver);//mudado*/
                            a.adicionaTransicao(m[i][j+1],1-prob[i],prob[i]);//mudado
                            a.adicionaTransicao(m[0][0],prob[i],1-prob[i]);

                            m[i][j].addAcao(a);
                           /* a2.setActionProbability( ProbMoveInsideRiver);
                            a.setActionProbability((1 - ProbMoveInsideRiver));
                            a2.setSuccessorState(m[i-1][j]);*/
                        } else if (m[i][j].getUtilityValueLinha() == 1) {
                            //a.setActionProbability(ProbMoveOutsideRiver);
                            //a2.setActionProbability((1 - ProbMoveOutsideRiver));
                            a.adicionaTransicao(m[i][j+1],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                            a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                            m[i][j].addAcao(a);
                        } else if (m[i][j].getUtilityValueLinha() == 3) {
                            //  a.setActionProbability(ProbMoveOutsideRiver);
                            // a2.setActionProbability((1 - ProbMoveOutsideRiver));
                            a.adicionaTransicao(m[i][j+1],1-prob[Ny-1],1-ProbMoveOutsideRiver);
                            a.adicionaTransicao(m[0][0],prob[Ny-1],ProbMoveOutsideRiver);
                            m[i][j].addAcao(a);
                        } else if (m[i][j].getUtilityValueLinha() == -3) {
                            //  a.setActionProbability(ProbMoveInsideRiver);
                            // a2.setActionProbability((1 - ProbMoveInsideRiver));
                            a.adicionaTransicao(m[i][j+1],ProbMoveInsideRiver,1-ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j],1-ProbMoveInsideRiver,ProbMoveInsideRiver);
                            m[i][j].addAcao(a);
                        }
                      /*  m[i][j].addAction(a);
                        m[i][j].addAction(a2);*/
                        else if (m[i][j].getUtilityValueLinha() == -2) {
                            a.adicionaTransicao(m[i][j + 1], ProbMoveInsideRiver, 1 - ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j], 1 - ProbMoveInsideRiver, ProbMoveInsideRiver);
                            m[i][j].addAcao(a);
                        }

                    }
                }
                //i - 1 > 0 &&
                if (j - 1 >= 0 && (m[i][j - 1].getUtilityValueLinha() == 1 || m[i][j - 1].getUtilityValueLinha() == 3 || m[i][j - 1].getUtilityValueLinha() == -3 || m[i][j - 1].getUtilityValueLinha() == 2))//todas acoes para cima
                {//mover ao oeste em qualquer lugar
                   /* ação a = new ação();
                    ação a2 = new ação();*/
                    // a.setCost(m[i][j - 1].getUtilityValueLinha().floatValue());
                    // a2.setCost(m[i][j - 1].getUtilityValueLinha().floatValue());
                   /* a.setCost(0);
                    a2.setCost(0);
                    a.setActionName("move-W");
                    a2.setActionName("move-W");*/
                    Acao a = new Acao("move-W");
                    a.setCost(cost);



                    if (m[i][j].getUtilityValueLinha() == -3) {
                   /*     a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[0][0]);
                        // a2.setSuccessorState(m[0][0]);
                        a.setActionProbability(1);
                        //    a2.setActionProbability(0);
                        m[i][j].addAction(a);
                        //  m[i][j].addAction(a2);*/
                        a.adicionaTransicao(m[0][0],1,0);
                        a.adicionaTransicao(m[0][0],0,1);
                        m[i][j].addAcao(a);
                    } else {

                       /* a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[i][j - 1]);
                        a2.setSuccessorState(m[i][j]);*/
                        if (m[i][j].getUtilityValueLinha() == 2) {
                            a.setCost(costriver);
                           // a.adicionaTransicao(m[i-1][j],1-ProbMoveInsideRiver,1-ProbMoveInsideRiver);//mudado
                          //  a.adicionaTransicao(m[0][0],ProbMoveInsideRiver ,ProbMoveInsideRiver);//mudado
                            a.adicionaTransicao(m[i][j-1],1-prob[i],prob[i]);//mudado
                            a.adicionaTransicao(m[0][0],prob[i] ,prob[i]);//mudado
                            m[i][j].addAcao(a);
                           /* a2.setActionProbability(ProbMoveInsideRiver);
                            a.setActionProbability((1 - ProbMoveInsideRiver));*/
                            //a2.setSuccessorState(m[i-1][j]);
                        } else if (m[i][j].getUtilityValueLinha() == 1) {
                           /* a.setActionProbability(ProbMoveOutsideRiver);
                            a2.setActionProbability((1 - ProbMoveOutsideRiver));*/
                            a.adicionaTransicao(m[i][j-1],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                            a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                            m[i][j].addAcao(a);

                        } else if (m[i][j].getUtilityValueLinha() == 3) {
                            /*a.setActionProbability(ProbMoveOutsideRiver);
                            a2.setActionProbability((1 - ProbMoveOutsideRiver));*/
                            a.adicionaTransicao(m[i][j-1],1-prob[Ny-1],1-ProbMoveOutsideRiver);
                            a.adicionaTransicao(m[0][0],prob[Ny-1],ProbMoveOutsideRiver);
                            m[i][j].addAcao(a);

                        } else if (m[i][j].getUtilityValueLinha() == -3) {
                           /* a.setActionProbability(ProbMoveInsideRiver);
                            a2.setActionProbability((1 - ProbMoveInsideRiver));*/
                            a.adicionaTransicao(m[i][j-1],ProbMoveInsideRiver,1-ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j],1-ProbMoveInsideRiver,ProbMoveInsideRiver);
                            m[i][j].addAcao(a);

                        }
                        else if (m[i][j].getUtilityValueLinha() == -2) {
                            a.adicionaTransicao(m[i][j + 1], ProbMoveInsideRiver, 1 - ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j], 1 - ProbMoveInsideRiver, ProbMoveInsideRiver);
                            m[i][j].addAcao(a);
                        }

                        else if (m[i][j].getUtilityValueLinha() == -2) {
                            a.adicionaTransicao(m[i][j + 1], ProbMoveInsideRiver, 1 - ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j], 1 - ProbMoveInsideRiver, ProbMoveInsideRiver);
                            m[i][j].addAcao(a);
                        }

                    }
                }


//&& j - 1 > 0
                if (i - 1 >= 0 && (m[i - 1][j].getUtilityValueLinha() == 1 || m[i - 1][j].getUtilityValueLinha() == 3 || m[i - 1][j].getUtilityValueLinha() == -3 || m[i - 1][j].getUtilityValueLinha() == 2))//todas acoes para cima
                {//mover ao sul em qualquer lugar
                    /*ação a = new ação();
                    ação a2 = new ação();
                    a.setCost(0);
                    a2.setCost(0);*/
                    Acao a = new Acao("move-S");
                    a.setCost(cost);

                    // a.setCost(m[i - 1][j].getUtilityValueLinha().floatValue());
                    //  a2.setCost(m[i - 1][j].getUtilityValueLinha().floatValue());
                  /*  a.setActionName("move-S");
                    a2.setActionName("move-S");*/
                    if (m[i][j].getUtilityValueLinha() == -3) {
                       /* a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[0][0]);
                        a2.setSuccessorState(m[0][0]);*/
                        /*m[i][j].addAction(a);
                        m[i][j].addAction(a2);*/
                        /*a.setActionProbability(1);
                        a2.setActionProbability(0);*/
                        a.adicionaTransicao(m[0][0],1,0);
                        a.adicionaTransicao(m[0][0],0,1);
                        m[i][j].addAcao(a);
                    } else {
                       /* a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[i - 1][j]);
                        a2.setSuccessorState(m[i][j]);*/

                        if (m[i][j].getUtilityValueLinha() == 2) {
                            a.setCost(costriver);
                          /*  a.setActionProbability(ProbMoveInsideRiver);
                            a2.setActionProbability((1-ProbMoveInsideRiver));
                            a.setSuccessorState(m[i-1][j]);
                            a2.setSuccessorState(m[i][j]);

                            m[i][j].addAction(a);
                            m[i][j].addAction(a2);*/


                            a.adicionaTransicao(m[i-1][j],1-prob[i],1-prob[i]);
                            a.adicionaTransicao(m[0][0],prob[i],prob[i]);
                            m[i][j].addAcao(a);


                        } else if (m[i][j].getUtilityValueLinha() == 1) {
                          /*  a.setActionProbability(ProbMoveOutsideRiver);
                            a2.setActionProbability((1 - ProbMoveOutsideRiver));**/
                            a.adicionaTransicao(m[i-1][j],ProbMoveOutsideRiver,1-ProbMoveOutsideRiver);
                            a.adicionaTransicao(m[i][j],1-ProbMoveOutsideRiver,ProbMoveOutsideRiver);
                            m[i][j].addAcao(a);




                        } else if (m[i][j].getUtilityValueLinha() == 3) {
                            /*a.setActionProbability(ProbMoveOutsideRiver);
                            a2.setActionProbability((1 - ProbMoveOutsideRiver));*/

                            a.adicionaTransicao(m[i-1][j],1-prob[Ny-1],1-ProbMoveOutsideRiver);
                            a.adicionaTransicao(m[0][0],prob[Ny-1],ProbMoveOutsideRiver);
                            m[i][j].addAcao(a);



                        } else if (m[i][j].getUtilityValueLinha() == -3) {
                           /* a.setActionProbability(ProbMoveInsideRiver);
                            a2.setActionProbability((1 - ProbMoveInsideRiver));*/
                            a.adicionaTransicao(m[i-1][j],ProbMoveInsideRiver,1-ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j],1-ProbMoveInsideRiver,ProbMoveInsideRiver);
                            m[i][j].addAcao(a);



                        }
                        else if (m[i][j].getUtilityValueLinha() == -2) {
                            a.adicionaTransicao(m[i][j + 1], ProbMoveInsideRiver, 1 - ProbMoveInsideRiver);
                            a.adicionaTransicao(m[i][j], 1 - ProbMoveInsideRiver, ProbMoveInsideRiver);
                            m[i][j].addAcao(a);
                        }


                    }
                }
                if (i - 1 < 0) {//mover ao  sul
                   /* ação a = new ação();
                    ação a2 = new ação();
                    a.setCost(0);
                    a2.setCost(0);*/
                    // a.setCost(m[i][j].getUtilityValueLinha().floatValue());
                    // a2.setCost(m[i][j].getUtilityValueLinha().floatValue());
                    Acao a = new Acao("move-S");
                    a.setCost(cost);
                    if (m[i][j].getUtilityValueLinha() == -3) {
                        /*a.setActionProbability(1);
                        a2.setActionProbability(0);
                        a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[0][0]);
                        a2.setSuccessorState(m[0][0]);
                        a.setActionName("move-S");
                        a2.setActionName("move-S");
                        m[i][j].addAction(a);*/
                        a.adicionaTransicao(m[0][0],1,0);
                        a.adicionaTransicao(m[0][0],0,1);
                        m[i][j].addAcao(a);

                        //   m[i][j].addAction(a2);
                    } else {
                      /*  a.setActionProbability(1);
                        a2.setActionProbability(0);
                        a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[i][j]);
                        a2.setSuccessorState(m[i][j]);
                        a.setActionName("move-S");
                        a2.setActionName("move-S");
                        m[i][j].addAction(a);*/
                        //  m[i][j].addAction(a2);
                        a.adicionaTransicao(m[i][j],1,0);
                        a.adicionaTransicao(m[i][j],0,1);
                        m[i][j].addAcao(a);
                    }
                }
                if (j - 1 < 0) {//mover a  oeste
                    /*ação a = new ação();
                    ação a2 = new ação();
                    a.setCost(0);
                    a2.setCost(0);*/
                    Acao a = new Acao("move-W");
                    a.setCost(cost);
                    // a.setCost(m[i][j].getUtilityValueLinha().floatValue());
                    //  a2.setCost(m[i][j].getUtilityValueLinha().floatValue());
                    if (m[i][j].getUtilityValueLinha() == -3) {
                       /* a.setActionProbability(1);
                        a2.setActionProbability(0);
                        a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[0][0]);
                        a2.setSuccessorState(m[0][0]);
                        a.setActionName("move-W");
                        a2.setActionName("move-W");
                        m[i][j].addAction(a);
                        //   m[i][j].addAction(a2);*/
                        a.adicionaTransicao(m[0][0],1,0);
                        a.adicionaTransicao(m[0][0],0,1);
                        m[i][j].addAcao(a);
                    } else {/*
                        a.setActionProbability(1);
                        a2.setActionProbability(0);
                        a.setCurrentState(m[i][j]);
                        a2.setCurrentState(m[i][j]);
                        a.setSuccessorState(m[i][j]);
                        a2.setSuccessorState(m[i][j]);
                        a.setActionName("move-W");
                        a2.setActionName("move-W");
                        m[i][j].addAction(a);
                        //     m[i][j].addAction(a2);*/
                        a.adicionaTransicao(m[i][j],1,0);
                        a.adicionaTransicao(m[i][j],0,1);
                        m[i][j].addAcao(a);
                    }
                }
                if (i + 1 >= Nx) {//mover ao norte

                    Acao a = new Acao("move-N");
                    a.setCost(cost);
                    a.adicionaTransicao(m[i][j],1,0);
                    a.adicionaTransicao(m[i][j],0,1);
                   /* ação a = new ação();
                    ação a2 = new ação();
                    a.setCost(0);
                    a2.setCost(0);
                    // a.setCost(m[i][j].getUtilityValueLinha().floatValue());
                    // a2.setCost(m[i][j].getUtilityValueLinha().floatValue());
                    a.setActionProbability(1);
                    a2.setActionProbability(0);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i][j]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-N");
                    a2.setActionName("move-N");
                    m[i][j].addAction(a);
                    //     m[i][j].addAction(a2);*/
                }
                if (j + 1 >= Ny) {//mover a direita
                 /*   ação a = new ação();
                    ação a2 = new ação();
                    a.setCost(0);
                    a2.setCost(0);
                    //     a.setCost(m[i][j].getUtilityValueLinha().floatValue());
                    //    a2.setCost(m[i][j].getUtilityValueLinha().floatValue());
                    a.setActionProbability(1);
                    a2.setActionProbability(0);
                    a.setCurrentState(m[i][j]);
                    a2.setCurrentState(m[i][j]);
                    a.setSuccessorState(m[i][j]);
                    a2.setSuccessorState(m[i][j]);
                    a.setActionName("move-E");
                    a2.setActionName("move-E");
                    m[i][j].addAction(a);
                    //   m[i][j].addAction(a2);*/
                    Acao a = new Acao("move-E");
                    a.setCost(cost);
                    a.adicionaTransicao(m[i][j],1,0);
                    a.adicionaTransicao(m[i][j],0,1);
                }


            }


        }
        for (int i = 0; i < Nx; i++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
        {//mover ao N em qualquer lugar
            for (int j = 0; j < Ny; j++)// M.RiverMap(1:M.Ny,1)=1;  M.RiverMap(1:M.Ny,M.Ny)=1; %land
            {
                if(m[i][j].actionSize()!=4)
                {
                    List<String>h= new LinkedList<String>();
                    int tamanho = m[i][j].actionSize();
                    for (int i2 = 0; i2 < tamanho; i2++) {//pega o maior Q entre as acoes possiveis.

                        Acao a2 = m[i][j].getAction(i2);

                        h.add(a2.getName());


                    }
                    if (!h.contains("move-E"))
                    {
                        Acao a = new Acao("move-E");
                        a.setCost(cost);
                        a.adicionaTransicao(m[i][j],1,0);
                        //  a.adicionaTransicao(m[i][j],0,1);
                        m[i][j].addAcao(a);
                    }
                    if (!h.contains("move-N")) {
                        Acao a = new Acao("move-N");
                        a.setCost(cost);
                        if (m[i][j].getUtilityValueLinha() == 3) {
                         //   Acao a = new Acao("move-N");
                           // a.setCost(cost);
                            a.adicionaTransicao(m[i][j], 1 - prob[Ny-1], 0);
                            a.adicionaTransicao(m[0][0], prob[Ny-1], 0);
                            //   a.adicionaTransicao(m[i][j],0,1);
                           // m[i][j].addAcao(a);
                        } else {
                            a.adicionaTransicao(m[i][j], 1, 0);
                        }
                        m[i][j].addAcao(a);
                    }
                    if (!h.contains("move-S"))
                    {
                        Acao a = new Acao("move-S");
                        a.setCost(cost);
                        a.adicionaTransicao(m[i][j],1,0);
                        // a.adicionaTransicao(m[i][j],0,1);
                        m[i][j].addAcao(a);
                    }
                    if (!h.contains("move-W")){
                        Acao a = new Acao("move-W");
                        a.setCost(cost);
                        a.adicionaTransicao(m[i][j],1,0);
                        a.adicionaTransicao(m[i][j],1,0);
                        //  a.adicionaTransicao(m[i][j],0,1);
                        m[i][j].addAcao(a);
                    }



                }
            }
        }


        // for (int i = 1; i < Ny - 1; i++) {
        //   m[0][i].getActions().clear();
        //  }
        //  for (int i = 1; i < Ny - 1; i++) {
        //      ação a = new ação();
        //       a.setActionName("move-I");
        //      a.setCost(0);
        //        a.setActionProbability(1);
        //       a.setCurrentState(m[0][i]);
        //       a.setSuccessorState(m[0][0]);
        //       m[0][i].addAction(a);

        //  }


        for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            System.out.println();

            m[0][Ny - 1].setGoal();//meta
            for (int y = 0; y < Ny; y++) {
                if (m[i][y].isGoal() == true) {
                    m[i][y].setCost(cost);
                } else {
                    m[i][y].setCost(cost);


                }

            }
        }
        m[0][Ny - 1].apagaMeta();//meta
        Acao a = new Acao("move-N");
        a.adicionaTransicao(m[0][Ny-1],1,0);
        Acao a2 = new Acao("move-S");
        a2.adicionaTransicao(m[0][Ny-1],1,0);
        Acao a3 = new Acao("move-E");
        a3.adicionaTransicao(m[0][Ny-1],1,0);
        Acao a4 = new Acao("move-W");
        a4.adicionaTransicao(m[0][Ny-1],1,0);

        m[0][Ny - 1].addAcao(a);
        m[0][Ny - 1].addAcao(a2);
        m[0][Ny - 1].addAcao(a3);
        m[0][Ny - 1].addAcao(a4);







        //System.out.println(m[0][Ny - 1].getUtilityValue());
        //System.out.println(m[0][Ny - 1].getName());
        //System.out.println(m[0][0].getName());
        //System.out.println(m[0][2].getName());
        //System.out.println(m[0][2].getUtilityValueLinha());
        //System.out.println(m[0][2].getActions().keySet());
        // System.out.println(m[1][0].getActions().keySet());


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
        p.setInitialState(m[0][0].getName());//estado inicial
        p.setFinalState(m[0][Ny - 1].getName());

        System.out.println ("Estado inicial: "+ p.getInitialState().getName());

        for (Estado e:p.getEstados().values())
        {


            for (int i=0; i<e.actionSize();i++)
            {
                System.out.println("Nome Estado: "+e.getName());
                System.out.println("meta: "+e.isGoal()+"  "+ e.actionSize());





                for (Transicao t : e.getAction(i).getTransitions())
                {


                    System.out.println("Nome Ação; " + e.getAction(i).getName());
                    t.setProbA(Math.round(t.getProbA()*100)/100.0d);


                    System.out.println("Probabilidade da Ação: "+ t.getState().getName() + "   "+t.getProbA() );






                }
                System.out.println();
            }

        }

        for (Estado e:p.getEstados().values())
        {
            System.out.println("Nome Estado: "+e.getName() + " "+ e.getCost());
        }







    }

    /*public Problem retornProblem() {

        for(String s : r.getStateSet().keySet())
        {
            for(String acs : r.getStateSet().get(s).getActions().keySet())
            {
                ArrayList<ação> açãos = r.getStateSet().get(s).getActions().get(acs);
                for(ação ac : açãos)
                {
                    ac.setActionProbability(Math.round(ac.getActionProbability()*100)/100.0d);

                }
            }
        }


        return r;
    }*/

    void printa() {
        System.out.println(Nx + "  " + Ny);
        for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            System.out.println();
            for (int y = 0; y < Ny; y++) {
                System.out.print(m[i][y].getUtilityValueLinha() + "  ");
            }


        }
    }

    /*void printa2() {
        for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            System.out.println();
            for (int y = 0; y < Ny; y++) {

                System.out.print(m[i][y].ggetActions().size());
                System.out.print(m[i][y].getActions().keySet());

            }


        }

    }*/

    /*void printa3() {

        for (int i = 0; i < Nx; i++)// M.RiverMap(1+1:M.Ny-1,1+1:M.Ny-1)=2; %river
        {
            System.out.println();
            for (int y = 0; y < Ny; y++) {

                //System.out.print(m[i][y].getActions().size());
                System.out.print(m[i][y].getActions().keySet());

            }


        }
    }*/



}



