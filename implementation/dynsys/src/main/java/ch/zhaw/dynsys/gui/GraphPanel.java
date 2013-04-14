package ch.zhaw.dynsys.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
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
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.SimulationListener;

public class GraphPanel extends ChartPanel implements SimulationListener {
	private static final long serialVersionUID = 1L;
	
	private double iteration = 0;
	private YIntervalSeriesCollection datasets = new YIntervalSeriesCollection();
	private JFreeChart chart;
	private XYPlot plot;

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
		DateAxis domainAxis = (DateAxis)plot.getDomainAxis();
		domainAxis.setVerticalTickLabels(true);
		domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.HOUR, 1, new SimpleDateFormat("hh:mm:ss")));
//		domainAxis.setAutoRange(true);
//		domainAxis.setLowerMargin(0);
//		domainAxis.setUpperMargin(0);
//		domainAxis.setRange(0, 50);
		domainAxis.setFixedAutoRange(60);
		plot.setDomainAxis(domainAxis);

		// legend
		LegendTitle legend = new LegendTitle(plot);
		legend.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		legend.setItemPaint(Color.gray);
		legend.setBackgroundPaint(Color.white);
		legend.setFrame(new BlockBorder(Color.gray));
		legend.setPosition(RectangleEdge.LEFT);
		XYTitleAnnotation ta = new XYTitleAnnotation(0.98, 0.02, legend,
				RectangleAnchor.BOTTOM_RIGHT);
		plot.addAnnotation(ta);

		// usability
		setMouseWheelEnabled(true);
		
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setChart(chart);
	}
	
	public void setZoom(double factor) {
		setZoomInFactor(factor);
		revalidate();
		repaint();
	}

	@Override
	public void evolved(final Collection<Culture> cultures) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	int i = 0;
		    	for (Culture culture : cultures) {
		    		if (i >= datasets.getSeriesCount()) {
		    			datasets.addSeries(new YIntervalSeries(culture.getName()));
		    		}
		    		YIntervalSeries dataset = datasets.getSeries(i);
		    		dataset.setKey(culture.getName());
		    		dataset.add(iteration, culture.getQuantity(), culture.getQuantity(), culture.getQuantity());
		    		
		    		i++;
		    	}
		    	
		    	// increase iteration
		    	iteration += 1;
		    	
		    	// update chart
		    	revalidate();
		    	repaint();
		    }
		  });
	}

	@Override
	public void started() {}
	@Override
	public void stoped() {}

	@Override
	public void clear() {
		datasets.removeAllSeries();
		iteration = 0;
	}

}
