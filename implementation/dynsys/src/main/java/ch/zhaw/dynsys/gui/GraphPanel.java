package ch.zhaw.dynsys.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar;
import java.util.Collection;

import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

import ch.zhaw.dynsys.simulation.Culture;

public class GraphPanel extends ChartPanel implements ch.zhaw.dynsys.simulation.Simulation.Listener {
	private static final int LASTEST_TIME_FRAME = 60000;

	private static final long serialVersionUID = 1L;
	
	private TimeSeriesCollection datasets = new TimeSeriesCollection();
	private JFreeChart chart;
	private XYPlot plot;
	private DateAxis domainAxis;

	public GraphPanel() {
		super(null);
		setPreferredSize(new Dimension(800, 600));

		// chart
		chart = ChartFactory.createTimeSeriesChart(null, null, null,
				datasets, false, false, false);

		// plot
		plot = (XYPlot) chart.getPlot();

		// colors
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.gray);
		plot.setDomainGridlinePaint(Color.gray);

		// range axis
		ValueAxis rangeAxis = plot.getRangeAxis();
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		// domain axis
		domainAxis = (DateAxis)plot.getDomainAxis();
		domainAxis.setLabel("Time");
		//domainAxis.setVerticalTickLabels(true);
		domainAxis.setAutoTickUnitSelection(true);
		domainAxis.setFixedAutoRange(LASTEST_TIME_FRAME);
		plot.setDomainAxis(domainAxis);

		// legend
		LegendTitle legend = new LegendTitle(plot);
		legend.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		legend.setItemPaint(Color.gray);
		legend.setBackgroundPaint(Color.white);
		legend.setFrame(new BlockBorder(Color.gray));
		legend.setPosition(RectangleEdge.LEFT);
		XYTitleAnnotation ta = new XYTitleAnnotation(0.01, 0.02, legend,
				RectangleAnchor.BOTTOM_LEFT);
		plot.addAnnotation(ta);

		// usability
		setMouseWheelEnabled(true);
		
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setChart(chart);
	}
	
	public void setViewAll(boolean flag) {
		if (flag) {
			domainAxis.setFixedAutoRange(0);
		} else {
			domainAxis.setFixedAutoRange(LASTEST_TIME_FRAME);
		}
	}
	
	
	public void clear() {
		datasets.removeAllSeries();
	}

	@Override
	public void evolved(final Collection<Culture> cultures, final long time) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	int i = 0;
		    	for (Culture culture : cultures) {
		    		if (i >= datasets.getSeriesCount()) {
		    			datasets.addSeries(new TimeSeries(culture.getName()));
		    		}
		    		TimeSeries dataset = datasets.getSeries(i);
		    		dataset.setKey(culture.getName());
		    		Calendar calendar = Calendar.getInstance();
		    		calendar.add(Calendar.MILLISECOND, (int)time);		    		
		    		dataset.add(new Millisecond(calendar.getTime()), culture.getValue());
		    		
		    		i++;
		    	}
		    	
		    	// update chart
		    	revalidate();
		    	repaint();
		    }
		  });
	}
}
