package com.nosliw.data.expression;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.data.core.criteria.HAPDataTypeCriteriaManager;
import com.nosliw.data.core.expression.HAPOperand;
import com.nosliw.data.core.expression.HAPOperandAttribute;
import com.nosliw.data.core.expression.HAPOperandConstant;
import com.nosliw.data.core.expression.HAPOperandOperation;
import com.nosliw.data.core.expression.HAPOperandReference;
import com.nosliw.data.core.expression.HAPOperandVariable;
import com.nosliw.data.expression.parser.NosliwExpressionParser;
import com.nosliw.data.expression.parser.SimpleNode;

/**
 * This is utility class to parse a expression as a string
 * The result of parsing is operand structure 
 */
public class HAPExpressionParser {

	  public static HAPOperand parseExpression(String expression, HAPDataTypeCriteriaManager criteriaMan){
		  SimpleNode root = null;
		  try{
			  InputStream is = new ByteArrayInputStream(expression.getBytes());
	          NosliwExpressionParser parser = new NosliwExpressionParser( is ) ;
	          root = parser.Expression("");
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  return null;
		  }
          return processExpressionNode(root, criteriaMan);
	  }
	  
	  private static HAPOperand processExpressionNode(SimpleNode parentNode, HAPDataTypeCriteriaManager criteriaMan){
		  HAPOperand out = null;
		  
		  ExpressionElements expressionEles = getExpressionElements(parentNode);

		  HAPOperand operand = null;
		  if(expressionEles.constantNode!=null){
			//it is a constant operand  
			 operand = new HAPOperandConstant((String)expressionEles.constantNode.jjtGetValue(), criteriaMan);
		  }
		  else if(expressionEles.variableNode!=null){
			  //it is a variable operand
			 operand = new HAPOperandVariable(((String)expressionEles.variableNode.jjtGetValue()), criteriaMan);
		  }
		  else if(expressionEles.referenceNode!=null){
			  //it is a variable operand
			 operand = new HAPOperandReference(((String)expressionEles.referenceNode.jjtGetValue()), criteriaMan);
		  }
		  else if(expressionEles.dataTypeNode!=null){
			  String dataTypeInfo = (String)expressionEles.dataTypeNode.jjtGetValue();
			  String operation = (String)expressionEles.nameNode.jjtGetValue();
			  operand = new HAPOperandOperation(dataTypeInfo, operation, getOperationParms(expressionEles.expressionNodes, criteriaMan), criteriaMan);
		  }
		  
		  out = processExpression1Node(expressionEles.expression1Node, operand, criteriaMan);
		  return out;
	  }

	  private static HAPOperand processExpression1Node(SimpleNode parentNode, HAPOperand aheadOperand, HAPDataTypeCriteriaManager criteriaMan){
		  if(isNodeEmpty(parentNode))  return aheadOperand;

		  HAPOperand out = null;
		  ExpressionElements expressionEles = getExpressionElements(parentNode);
		  String name = (String)expressionEles.nameNode.jjtGetValue();
		  HAPOperand operand = null;
		  if("function".equals(parentNode.jjtGetValue())){
			  //function call
			  operand = new HAPOperandOperation(aheadOperand, name, getOperationParms(expressionEles.expressionNodes, criteriaMan), criteriaMan);
		  }
		  else{
			  //path
			  operand = new HAPOperandAttribute(aheadOperand, name, criteriaMan);
		  }
		  out = processExpression1Node(expressionEles.expression1Node, operand, criteriaMan);
		  return out;
	  }

	  private static Map<String, HAPOperand> getOperationParms(List<Parm> expressionParms, HAPDataTypeCriteriaManager criteriaMan){
		  Map<String, HAPOperand> out = new LinkedHashMap<String, HAPOperand>();
		  for(Parm parm : expressionParms){
			  HAPOperand op = processExpressionNode(parm.valuNode, criteriaMan);
			  out.put(parm.name, op);
		  }
		  return out;
	  }
	  
	  private static ExpressionElements getExpressionElements(SimpleNode parentNode){
		  ExpressionElements out = new ExpressionElements();
		  
		  for(int i=0; i<parentNode.jjtGetNumChildren(); i++){
			  SimpleNode childNode = (SimpleNode)parentNode.jjtGetChild(i);
			  switch(childNode.getId()){
			  case NosliwExpressionParser.JJTCONSTANT:
			  {
				  out.constantNode = childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTVARIABLE:
			  {
				  out.variableNode = childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTREFERENCE:
			  {
				  out.referenceNode = childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTNAME:
			  {
				  out.nameNode =childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTDATATYPE:
			  {
				  out.dataTypeNode =childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTEXPRESSION1:
			  {
				  out.expression1Node = childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTPARAMETER:
			  {
				  out.expressionNodes.add(processParm(childNode));
				  break;
			  }
			  
			  }
		  }
		  return out;
	  }
	  
	  private static Parm processParm(SimpleNode node){
		  Parm out = new Parm();
		  for(int i=0; i<node.jjtGetNumChildren(); i++){
			  SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
			  switch(childNode.getId()){
			  case NosliwExpressionParser.JJTPARMNAME:
			  {
				  out.name = (String)childNode.jjtGetValue();
				  break;
			  }
			  case NosliwExpressionParser.JJTEXPRESSION:
			  {
				  out.valuNode = childNode;
				  break;
			  }
			  }
		  }
		  return out;
	  }
	  
	  
	  private static boolean isNodeEmpty(SimpleNode node){
		  if(node.jjtGetNumChildren()==0)   return true;
		  return false;
	  }
}

class ExpressionElements{
	  SimpleNode constantNode;
	  SimpleNode variableNode;
	  SimpleNode referenceNode;
	  SimpleNode nameNode;
	  SimpleNode dataTypeNode;
	  List<Parm> expressionNodes = new ArrayList<Parm>();
	  SimpleNode expression1Node;
}


class Parm{
	String name;
	SimpleNode valuNode;
}
