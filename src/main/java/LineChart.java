import Models.Match;
import Models.Player;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class LineChart extends JFrame {

    public LineChart( String applicationTitle , String chartTitle, List<Player> pList, List<Match> mList) {
        super(applicationTitle);

        JPanel chartPanel = createChartPanel(pList, mList);

        chartPanel.setPreferredSize( new java.awt.Dimension( 1280 , 600 ) );
        setContentPane( chartPanel );
    }

    public static XYPlot createWinQualityDataset(List<Player> list, List<Match> matches) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeriesCollection datasetQ = new XYSeriesCollection();
        XYSeries winSeries = new XYSeries("Wins");
        XYSeries quality = new XYSeries("Quality");
        for (Player p: list) {
            winSeries.add(p.getSkill(), p.getWins());
        }

        // Calculate the AVG match quality for each skill point
        Hashtable<Integer, Double> totals = new Hashtable<Integer, Double>();
        for (Match m : matches) {
            int p1Skill = m.getPlayer1().getSkill();
            int p2Skill = m.getPlayer2().getSkill();
            if (totals.get(p1Skill) == null)
                totals.put(p1Skill, m.getQaulity());
            else
                totals.put(p1Skill, totals.get(p1Skill) + m.getQaulity());

            if (totals.get(p2Skill) == null)
                totals.put(p2Skill, m.getQaulity());
            else
                totals.put(p2Skill, totals.get(p2Skill) + m.getQaulity());
        }

        for (Map.Entry<Integer, Double> e : totals.entrySet()) {
            double avg = e.getValue() / 2000.0;
            quality.add((int) e.getKey(), avg);
        }

        dataset.addSeries(winSeries);
        datasetQ.addSeries(quality);
        XYPlot plot = new XYPlot();
        plot.setDataset(0, dataset);
        plot.setDataset(1, datasetQ);

        plot.setRenderer(0, new XYSplineRenderer());//use default fill paint for first series
        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        splinerenderer.setSeriesFillPaint(0, Color.BLUE);
        plot.setRenderer(1, splinerenderer);
        plot.setRangeAxis(0, new NumberAxis("Series 1"));
        plot.setRangeAxis(1, new NumberAxis("Series 2"));
        plot.setDomainAxis(new NumberAxis("X Axis"));

        // sets plot background
        plot.setBackgroundPaint(Color.DARK_GRAY);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);


        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);

        return plot;
    }


    private JPanel createChartPanel(List<Player> pList, List<Match> mList) { // this method will create the chart panel containin the graph
        String chartTitle = "Objects Movement Chart";
        String xAxisLabel = "Skill";
        String yAxisLabel = "Wins";

        XYPlot dataset = createWinQualityDataset(pList, mList);

        JFreeChart chart = new JFreeChart("Chart", getFont(), dataset, true);

        //customizeChart(chart);

        return new ChartPanel(chart);
    }

}