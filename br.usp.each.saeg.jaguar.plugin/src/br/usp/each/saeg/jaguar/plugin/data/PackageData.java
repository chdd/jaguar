package br.usp.each.saeg.jaguar.plugin.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.Position;

import br.usp.each.saeg.jaguar.plugin.markers.CodeMarkerFactory;

/**
 * @author Higor Amario (higoramario@gmail.com)
 */
public class PackageData{
	
	private String value;
    private float score;
    private List<ClassData> classData = new ArrayList<ClassData>();
    private String name;
    private IResource resource;
    private String logLine;
    private boolean enabled = true;
    
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public float getScore() {
        return score;
    }
    public void setScore(float score) {
        this.score = score;
    }

    public float getRoundedScore(int scale) {
        return roundScore(score,scale);
    }
    
    public List<ClassData> getClassData() {
        return classData;
    }
    public void setClassData(List<ClassData> classData) {
        if (null != classData) {
            this.classData = classData;
        }
    }

    public void setResource(IResource resource) {
        this.resource = resource;
    }

    public boolean isScoreBetween(float min, float max) {
        return Float.compare(score, max) <= 0 && Float.compare(score, min) >= 0;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getLogLine() {
        return logLine;
    }
    public void setLogLine(String logLine) {
        this.logLine = logLine;
    }
    
    public void enable() {
        if (enabled) {
            return;
        }
        enabled = true;
    }

    public void disable() {
        if (!enabled) {
            return;
        }
        enabled = false;
    }
    
    public boolean isEnabled(){
    	return enabled;
    }
    
    public float roundScore(float originalScore, int scale){
    	BigDecimal roundedScore = new BigDecimal(Float.toString(originalScore));
    	roundedScore = roundedScore.setScale(scale, BigDecimal.ROUND_DOWN);
    	return roundedScore.floatValue();
    }
    
    @Override
	public String toString() {
		return "Package [name=" + name + ", score=" + score + "]";
	}
}
