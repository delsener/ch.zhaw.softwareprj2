import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;

public class GraphMockup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JFreeChart");

		YIntervalSeriesCollection xydataset = new YIntervalSeriesCollection();
		YIntervalSeries zucker = new YIntervalSeries("Zucker");
		YIntervalSeries hefe = new YIntervalSeries("Hefe");
		YIntervalSeries baecker = new YIntervalSeries("Bäcker");
		xydataset.addSeries(zucker);
		xydataset.addSeries(hefe);
		xydataset.addSeries(baecker);

		for (int i = 0; i < 10; i++) {
			zucker.add(i, 1000 - Math.pow(2, i), 0, 100);
			hefe.add(i, Math.pow(2, i) - i * 4, 0, 100);
			baecker.add(i, i * 27, 0, 100);
		}

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Populations Dynamik", "Zeit", "Population", xydataset, true,
				true, false);

		ChartPanel chartPanel = new ChartPanel(chart);
		frame.add(chartPanel);

		frame.setSize(600, 400);
		frame.setVisible(true);
	}

}
