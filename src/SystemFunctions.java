/********************************************************************************
   Copyright 2011-2012 Leonidas Fegaras, University of Texas at Arlington

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   File: Functions.java
   System-defined functions
   Programmer: Leonidas Fegaras, UTA
   Date: 11/04/10 - 03/31/11
   Call importClass to import a new class with user-defined methods

********************************************************************************/

package hadoop.mrql;

import java.util.*;
import java.lang.Math;
import java.lang.reflect.Method;
import Gen.*;


// System functions must be from MRData to MRData
final public class SystemFunctions {

    // used for shortcutting sync in bsp supersteps
    final public static Bag bsp_empty_bag = new Bag();
    final public static MR_bool bsp_true_value = new MR_bool(true);
    final public static MR_bool bsp_false_value = new MR_bool(false);

    final static MRData null_value = new Tuple(0);
    final static MR_bool true_value = new MR_bool(true);
    final static MR_bool false_value = new MR_bool(false);

    private static void error ( String s ) {
	System.err.println("*** "+s);
	throw new Error(s);
    }

    static Random random = new Random();

    public static MR_bool synchronize ( MR_bool mr_exit ) {
	return Evaluator.synchronize(mr_exit);
    }

    public static MR_bool lt ( MR_short x, MR_short y ) { return (x.get() < y.get()) ? true_value : false_value; }
    public static MR_bool lt ( MR_int x, MR_int y ) { return (x.get() < y.get()) ? true_value : false_value; }
    public static MR_bool lt ( MR_long x, MR_long y ) { return (x.get() < y.get()) ? true_value : false_value; }
    public static MR_bool lt ( MR_float x, MR_float y ) { return (x.get() < y.get()) ? true_value : false_value; }
    public static MR_bool lt ( MR_double x, MR_double y ) { return (x.get() < y.get()) ? true_value : false_value; }

    public static MR_bool gt ( MR_short x, MR_short y ) { return (x.get() > y.get()) ? true_value : false_value; }
    public static MR_bool gt ( MR_int x, MR_int y ) { return (x.get() > y.get()) ? true_value : false_value; }
    public static MR_bool gt ( MR_long x, MR_long y ) { return (x.get() > y.get()) ? true_value : false_value; }
    public static MR_bool gt ( MR_float x, MR_float y ) { return (x.get() > y.get()) ? true_value : false_value; }
    public static MR_bool gt ( MR_double x, MR_double y ) { return (x.get() > y.get()) ? true_value : false_value; }

    public static MR_bool leq ( MR_short x, MR_short y ) { return (x.get() <= y.get()) ? true_value : false_value; }
    public static MR_bool leq ( MR_int x, MR_int y ) { return (x.get() <= y.get()) ? true_value : false_value; }
    public static MR_bool leq ( MR_long x, MR_long y ) { return (x.get() <= y.get()) ? true_value : false_value; }
    public static MR_bool leq ( MR_float x, MR_float y ) { return (x.get() <= y.get()) ? true_value : false_value; }
    public static MR_bool leq ( MR_double x, MR_double y ) { return (x.get() <= y.get()) ? true_value : false_value; }

    public static MR_bool geq ( MR_short x, MR_short y ) { return (x.get() >= y.get()) ? true_value : false_value; }
    public static MR_bool geq ( MR_int x, MR_int y ) { return (x.get() >= y.get()) ? true_value : false_value; }
    public static MR_bool geq ( MR_long x, MR_long y ) { return (x.get() >= y.get()) ? true_value : false_value; }
    public static MR_bool geq ( MR_float x, MR_float y ) { return (x.get() >= y.get()) ? true_value : false_value; }
    public static MR_bool geq ( MR_double x, MR_double y ) { return (x.get() >= y.get()) ? true_value : false_value; }

    public static MR_bool eq ( MR_short x, MR_short y ) { return (x.get() == y.get()) ? true_value : false_value; }
    public static MR_bool eq ( MR_int x, MR_int y ) { return (x.get() == y.get()) ? true_value : false_value; }
    public static MR_bool eq ( MR_long x, MR_long y ) { return (x.get() == y.get()) ? true_value : false_value; }
    public static MR_bool eq ( MR_float x, MR_float y ) { return (x.get() == y.get()) ? true_value : false_value; }
    public static MR_bool eq ( MR_double x, MR_double y ) { return (x.get() == y.get()) ? true_value : false_value; }

    public static MR_bool neq ( MR_short x, MR_short y ) { return (x.get() != y.get()) ? true_value : false_value; }
    public static MR_bool neq ( MR_int x, MR_int y ) { return (x.get() != y.get()) ? true_value : false_value; }
    public static MR_bool neq ( MR_long x, MR_long y ) { return (x.get() != y.get()) ? true_value : false_value; }
    public static MR_bool neq ( MR_float x, MR_float y ) { return (x.get() != y.get()) ? true_value : false_value; }
    public static MR_bool neq ( MR_double x, MR_double y ) { return (x.get() != y.get()) ? true_value : false_value; }

    public static MR_bool eq ( MRData x, MRData y ) { return (x.compareTo(y) == 0) ? true_value : false_value; }
    public static MR_bool neq ( MRData x, MRData y ) { return (x.compareTo(y) != 0) ? true_value : false_value; }
    public static MR_bool lt ( MRData x, MRData y ) { return (x.compareTo(y) < 0) ? true_value : false_value; }
    public static MR_bool leq ( MRData x, MRData y ) { return (x.compareTo(y) <= 0) ? true_value : false_value; }
    public static MR_bool gt ( MRData x, MRData y ) { return (x.compareTo(y) > 0) ? true_value : false_value; }
    public static MR_bool geq ( MRData x, MRData y ) { return (x.compareTo(y) >= 0) ? true_value : false_value; }

    public static MR_string plus ( MR_string x, MR_string y ) { return new MR_string(x.get()+y.get()); }

    public static Bag plus ( Bag x, Bag y ) { return x.union(y); }

    public static MR_short plus ( MR_short x, MR_short y ) { return new MR_short((short)(x.get()+y.get())); }
    public static MR_int plus ( MR_int x, MR_int y ) { return new MR_int(x.get()+y.get()); }
    public static MR_long plus ( MR_long x, MR_long y ) { return new MR_long(x.get()+y.get()); }
    public static MR_float plus ( MR_float x, MR_float y ) { return new MR_float(x.get()+y.get()); }
    public static MR_double plus ( MR_double x, MR_double y ) { return new MR_double(x.get()+y.get()); }

    public static MR_short minus ( MR_short x, MR_short y ) { return new MR_short((short)(x.get()-y.get())); }
    public static MR_int minus ( MR_int x, MR_int y ) { return new MR_int(x.get()-y.get()); }
    public static MR_long minus ( MR_long x, MR_long y ) { return new MR_long(x.get()-y.get()); }
    public static MR_float minus ( MR_float x, MR_float y ) { return new MR_float(x.get()-y.get()); }
    public static MR_double minus ( MR_double x, MR_double y ) { return new MR_double(x.get()-y.get()); }

    public static MR_short times ( MR_short x, MR_short y ) { return new MR_short((short)(x.get()*y.get())); }
    public static MR_int times ( MR_int x, MR_int y ) { return new MR_int(x.get()*y.get()); }
    public static MR_long times ( MR_long x, MR_long y ) { return new MR_long(x.get()*y.get()); }
    public static MR_float times ( MR_float x, MR_float y ) { return new MR_float(x.get()*y.get()); }
    public static MR_double times ( MR_double x, MR_double y ) { return new MR_double(x.get()*y.get()); }

    public static MR_short div ( MR_short x, MR_short y ) { return new MR_short((short)(x.get()/y.get())); }
    public static MR_int div ( MR_int x, MR_int y ) { return new MR_int(x.get()/y.get()); }
    public static MR_long div ( MR_long x, MR_long y ) { return new MR_long(x.get()/y.get()); }
    public static MR_float div ( MR_float x, MR_float y ) { return new MR_float(x.get()/y.get()); }
    public static MR_double div ( MR_double x, MR_double y ) { return new MR_double(x.get()/y.get()); }

    public static MR_int mod ( MR_int x, MR_int y ) { return new MR_int(x.get() % y.get()); }
    public static MR_long mod ( MR_long x, MR_long y ) { return new MR_long(x.get() % y.get()); }

    public static MR_short min ( MR_short x, MR_short y ) { return new MR_short((short)(Math.min(x.get(),y.get()))); }
    public static MR_int min ( MR_int x, MR_int y ) { return new MR_int(Math.min(x.get(),y.get())); }
    public static MR_long min ( MR_long x, MR_long y ) { return new MR_long(Math.min(x.get(),y.get())); }
    public static MR_float min ( MR_float x, MR_float y ) { return new MR_float(Math.min(x.get(),y.get())); }
    public static MR_double min ( MR_double x, MR_double y ) { return new MR_double(Math.min(x.get(),y.get())); }

    public static MR_short max ( MR_short x, MR_short y ) { return new MR_short((short)(Math.max(x.get(),y.get()))); }
    public static MR_int max ( MR_int x, MR_int y ) { return new MR_int(Math.max(x.get(),y.get())); }
    public static MR_long max ( MR_long x, MR_long y ) { return new MR_long(Math.max(x.get(),y.get())); }
    public static MR_float max ( MR_float x, MR_float y ) { return new MR_float(Math.max(x.get(),y.get())); }
    public static MR_double max ( MR_double x, MR_double y ) { return new MR_double(Math.max(x.get(),y.get())); }

    public static MR_double sin ( MR_double x ) { return new MR_double(Math.sin(x.get())); }
    public static MR_double cos ( MR_double x ) { return new MR_double(Math.cos(x.get())); }
    public static MR_double tan ( MR_double x ) { return new MR_double(Math.tan(x.get())); }
    public static MR_double asin ( MR_double x ) { return new MR_double(Math.asin(x.get())); }
    public static MR_double acos ( MR_double x ) { return new MR_double(Math.acos(x.get())); }
    public static MR_double atan ( MR_double x ) { return new MR_double(Math.atan(x.get())); }
    public static MR_double pow ( MR_double x, MR_double y ) { return new MR_double(Math.pow(x.get(),y.get())); }
    public static MR_double sqrt ( MR_double x ) { return new MR_double(Math.sqrt(x.get())); }
    public static MR_double ceil ( MR_double x ) { return new MR_double(Math.ceil(x.get())); }
    public static MR_double floor ( MR_double x ) { return new MR_double(Math.floor(x.get())); }
    public static MR_double rint ( MR_double x ) { return new MR_double(Math.rint(x.get())); }
    public static MR_int round ( MR_float x ) { return new MR_int((int)Math.round(x.get())); }
    public static MR_long round ( MR_double x ) { return new MR_long(Math.round(x.get())); }

    public static MR_short plus ( MR_short x ) { return x; }
    public static MR_int plus ( MR_int x ) { return x; }
    public static MR_long plus ( MR_long x ) { return x; }
    public static MR_float plus ( MR_float x ) { return x; }
    public static MR_double plus ( MR_double x ) { return x; }

    public static MR_short minus ( MR_short x ) { return new MR_short((short)-x.get()); }
    public static MR_int minus ( MR_int x ) { return new MR_int(-x.get()); }
    public static MR_long minus ( MR_long x ) { return new MR_long(-x.get()); }
    public static MR_float minus ( MR_float x ) { return new MR_float(-x.get()); }
    public static MR_double minus ( MR_double x ) { return new MR_double(-x.get()); }

    public static MR_short abs ( MR_short x ) { return new MR_short((short) Math.abs(x.get())); }
    public static MR_int abs ( MR_int x ) { return new MR_int(Math.abs(x.get())); }
    public static MR_long abs ( MR_long x ) { return new MR_long(Math.abs(x.get())); }
    public static MR_float abs ( MR_float x ) { return new MR_float(Math.abs(x.get())); }
    public static MR_double abs ( MR_double x ) { return new MR_double(Math.abs(x.get())); }

    public static Inv inv ( MRData x ) { return new Inv(x); }

    public static MR_bool and ( MR_bool x, MR_bool y ) { return (x.get()) ? y : false_value; }
    public static MR_bool or ( MR_bool x, MR_bool y ) { return (x.get()) ? true_value : y; }
    public static MR_bool not ( MR_bool x ) { return (x.get()) ? false_value : true_value; }

    public static MR_bool toBool ( MR_string s ) { return (s.get().equals("true")) ? true_value : false_value; }
    public static MR_short toShort ( MR_string s ) { return new MR_short(Short.parseShort(s.get())); }
    public static MR_int toInt ( MR_string s ) { return new MR_int(Integer.parseInt(s.get())); }
    public static MR_long toLong ( MR_string s ) { return new MR_long(Long.parseLong(s.get())); }
    public static MR_float toFloat ( MR_string s ) { return new MR_float(Float.parseFloat(s.get())); }
    public static MR_double toDouble ( MR_string s ) { return new MR_double(Double.parseDouble(s.get())); }

    public static MR_int random ( MR_int n ) {
	int v = random.nextInt(n.get());
	return new MR_int(v);
    }

    public static MR_float log ( MR_float n ) { return new MR_float(Math.log(n.get())); }
    public static MR_double log ( MR_double n ) { return new MR_double(Math.log(n.get())); }
    public static MR_float exp ( MR_float n ) { return new MR_float(Math.exp(n.get())); }
    public static MR_double exp ( MR_double n ) { return new MR_double(Math.exp(n.get())); }

    public static MR_string string ( MRData x ) { return new MR_string(x.toString()); }

    public static MR_bool contains ( MR_string x, MR_string y ) { return new MR_bool(x.get().contains(y.get())); }
    public static MR_int length ( MR_string x ) { return new MR_int(x.get().length()); }
    public static MR_string substring ( MR_string x, MR_int b, MR_int e ) { return new MR_string(x.get().substring(b.get(),e.get())); }

    public static MR_bool exists ( Bag s ) {
	return (s.iterator().hasNext()) ? true_value : false_value;
    }

    public static MR_bool some ( Bag x ) {
	for ( MRData e: x )
	    if (e instanceof MR_bool)
		if (((MR_bool)e).get())
		    return true_value;
	return false_value;
    }

    public static MR_bool all ( Bag x ) {
	for ( MRData e: x )
	    if (e instanceof MR_bool)
		if (!((MR_bool)e).get())
		    return false_value;
	return true_value;
    }

    public static MR_bool member ( MRData e, Bag s ) {
	return (s.contains(e)) ? true_value : false_value;
    }

    public static MR_long count ( Bag s ) {
	if (s.materialized())
	    return new MR_long(s.size());
	long i = 0;
	for ( MRData e: s )
	    i++;
	return new MR_long(i);
    }

    public static MRData index ( Bag b, MR_int mi ) {
	int i = mi.get();
	if (i < 0)
	    throw new Error("wrong index: "+i);
	if (b.materialized())
	    return b.get(i);
	int k = 0;
	for ( MRData e: b )
	    if (k++ == i)
		return e;
	throw new Error("wrong index: "+i);
    }

    public static Bag range ( Bag b, MR_int mi, MR_int mj ) {
	int i = mi.get();
	int j = mj.get();
	if (j < i)
	    throw new Error("wrong range indexes: "+i+","+j);
	Bag bag = new Bag(j-i+1);
	int k = 0;
	for ( MRData e: b ) {
	    if (k >= i && k <= j)
		bag.add(e);
	    k++;
	};
	return bag;
    }

    public static Bag union ( Bag x, Bag y ) {
	return x.union(y);
    }

    public static Bag intersect ( Bag x, Bag y ) {
	x.materialize();
	Bag s = new Bag();
	for ( MRData e: y )
	    if (x.contains(e))
		s.add(e);
	return s;
    }

    public static Bag except ( Bag x, Bag y ) {
	y.materialize();
	Bag s = new Bag();
	for ( MRData e: x )
	    if (!y.contains(e))
		s.add(e);
	return s;
    }

    public static Bag materialize ( Bag x ) {
	x.materialize();
	return x;
    }

    public static MRData coerce ( MRData from, MR_int type ) {
	byte tp = (byte)type.get();
	if (from instanceof MR_short) {
	    if (tp == MRContainer.BYTE)
		return new MR_byte((byte)((MR_short)from).get());
	    else if (tp == MRContainer.SHORT)
		return from;
	    else if (tp == MRContainer.INT)
		return new MR_int((int)((MR_short)from).get());
	    else if (tp == MRContainer.LONG)
		return new MR_long((long)((MR_short)from).get());
	    else if (tp == MRContainer.FLOAT)
		return new MR_float((float)((MR_short)from).get());
	    else if (tp == MRContainer.DOUBLE)
		return new MR_double((double)((MR_short)from).get());
	} else if (from instanceof MR_int) {
	    if (tp == MRContainer.BYTE)
		return new MR_byte((byte)((MR_int)from).get());
	    else if (tp == MRContainer.SHORT)
		return new MR_short((short)((MR_int)from).get());
	    else if (tp == MRContainer.INT)
		return from;
	    else if (tp == MRContainer.LONG)
		return new MR_long((long)((MR_int)from).get());
	    else if (tp == MRContainer.FLOAT)
		return new MR_float((float)((MR_int)from).get());
	    else if (tp == MRContainer.DOUBLE)
		return new MR_double((double)((MR_int)from).get());
	} else if (from instanceof MR_long) {
	    if (tp == MRContainer.BYTE)
		return new MR_byte((byte)((MR_long)from).get());
	    else if (tp == MRContainer.SHORT)
		return new MR_short((short)((MR_long)from).get());
	    else if (tp == MRContainer.INT)
		return new MR_int((int)((MR_long)from).get());
	    else if (tp == MRContainer.LONG)
		return from;
	    else if (tp == MRContainer.FLOAT)
		return new MR_float((float)((MR_long)from).get());
	    else if (tp == MRContainer.DOUBLE)
		return new MR_double((double)((MR_long)from).get());
	} else if (from instanceof MR_float) {
	    if (tp == MRContainer.BYTE)
		return new MR_byte((byte)((MR_float)from).get());
	    else if (tp == MRContainer.SHORT)
		return new MR_short((short)((MR_float)from).get());
	    else if (tp == MRContainer.INT)
		return new MR_int((int)((MR_float)from).get());
	    else if (tp == MRContainer.LONG)
		return new MR_long((long)((MR_float)from).get());
	    if (tp == MRContainer.FLOAT)
		return from;
	    else if (tp == MRContainer.DOUBLE)
		return new MR_double((double)((MR_float)from).get());
	} else if (from instanceof MR_double) {
	    if (tp == MRContainer.BYTE)
		return new MR_byte((byte)((MR_double)from).get());
	    else if (tp == MRContainer.SHORT)
		return new MR_short((short)((MR_double)from).get());
	    else if (tp == MRContainer.INT)
		return new MR_int((int)((MR_double)from).get());
	    else if (tp == MRContainer.LONG)
		return new MR_long((long)((MR_double)from).get());
	    if (tp == MRContainer.FLOAT)
		return new MR_float((float)((MR_double)from).get());
	    if (tp == MRContainer.DOUBLE)
		return from;
	};
	error("Cannot up-coerce the numerical value "+from);
	return null;
    }
 
    // used in avg
    public static MR_double avg_value ( MRData t ) {
	MR_double sum = (MR_double)((Tuple)t).first();
	MR_long count = (MR_long)((Tuple)t).second();
	return new MR_double(sum.get()/count.get());
    }

    public static MR_string text ( Union node ) {
	if (node.tag() == 1)
	    return (MR_string)(node.value());
	Bag b = (Bag)((Tuple)node.value()).get(2);
	String s = "";
	for ( MRData e: b )
	    if (((Union)e).tag() == 1)
		s += ((MR_string)(((Union)e).value())).get();
	return new MR_string(s);
    }

    public static MR_string text ( Bag nodes ) {
	MR_string b = new MR_string("");
	for ( MRData e: nodes )
	    b = plus(b,text((Union)e));
	return b;
    }

    public static MR_string tag ( Union node ) {
	if (node.tag() == 1)
	    error("Cannot extract the tagname of a CData: "+node);
	return (MR_string)((Tuple) node.value()).get(0);
    }

    public static MR_string XMLattribute ( MR_string tagname, Union node ) {
	if (node.tag() == 1)
	    error("Element "+node+" does not have attributes");
	Tuple t = (Tuple)node.value();
	String tag = tagname.get();
	for ( MRData c: (Bag)t.get(1) ) {
	    Tuple p = (Tuple)c;
	    if (tag.equals(((MR_string)(p.get(0))).get()))
		return new MR_string(((MR_string)p.get(1)).get());
	};
	error("Element "+node+" does not have attribute "+tagname);
	return null;
    }

    public static Bag XMLattributes ( MR_string tagname, Union node ) {
	if (node.tag() == 1)
	    return new Bag();
	Tuple t = (Tuple)node.value();
	Bag b = new Bag();
	String tag = tagname.get();
	for ( MRData c: (Bag)t.get(1) ) {
	    Tuple p = (Tuple)c;
	    if (tag.equals("*") || tag.equals(((MR_string)(p.get(0))).get()))
		b.add(p.get(1));
	};
	return b;
    }

    public static Bag XMLattributes ( MR_string tagname, Bag nodes ) {
	Bag b = new Bag();
	for ( MRData e: nodes )
	    for (MRData c: XMLattributes(tagname,(Union)e))
		b.add(c);
	return b;
    }

    public static Bag XMLattribute ( MR_string tagname, Bag nodes ) {
	Bag b = new Bag();
	for ( MRData e: nodes )
	    for (MRData c: XMLattributes(tagname,(Union)e))
		b.add(c);
	return b;
    }

    public static Bag XMLchildren ( MR_string tagname, Union node ) {
	if (node.tag() == 1)
	    return new Bag();
	Tuple t = (Tuple)node.value();
	Bag b = new Bag();
	String tag = tagname.get();
	for ( MRData c: (Bag)t.get(2) )
	    if (((Union)c).tag() == 0) {
		Tuple s = (Tuple)(((Union)c).value());
		if (tag.equals("*") || (((MR_string)(s.get(0))).get()).equals(tag))
		    b.add(c);
	    };
	return b;
    }

    public static Bag XMLchildren ( MR_string tagname, Bag nodes ) {
	Bag b = new Bag();
	for ( MRData e: nodes )
	    for (MRData c: XMLchildren(tagname,(Union)e))
		b.add(c);
	return b;
    }

    public static MRData fold ( Lambda c, MRData z, Bag s ) {
	MRData v = z;
	for ( MRData e: s )
	    z = c.lambda().eval(new Tuple(z,e));
	return z;
    }
}


final class MethodInfo implements Comparable<MethodInfo> {
    public String name;
    public Trees signature;
    public Method method;
    MethodInfo ( String n, Trees s, Method m ) { name=n; signature=s; method=m; }
    public int compareTo ( MethodInfo x )  {
	return name.compareTo(x.name);
    }
    public boolean equals ( Object x ) {
	return name.equals(((MethodInfo)x).name);
    }
}


final class ClassImporter {
    final static boolean trace_imported_methods = false;

    final static String[] object_methods
	= { "hashCode", "getClass", "wait", "equals", "toString", "notify", "notifyAll" };

    static Vector<MethodInfo> methods = new Vector<MethodInfo>();

    public static void load_classes () {
	if (methods == null)
	    methods = new Vector<MethodInfo>();
	if (methods.size() == 0) {
	    importClass("hadoop.mrql.SystemFunctions");
	    //****** import your classes with user-defined functions here
	}
    }

    static boolean object_method ( String s ) {
	for (int i = 0; i < object_methods.length; i++)
	    if (object_methods[i].equals(s))
		return true;
	return false;
    }

    static Tree getType ( Class<?> c ) {
	String cn = c.getCanonicalName();
	Class<?>[] inf = c.getInterfaces();
	if (cn.equals("hadoop.mrql.MRData"))
	    return new VariableLeaf("any");
	if (cn.startsWith("hadoop.mrql.MR_"))
	    return new VariableLeaf(cn.substring(15));
	if (cn.equals("hadoop.mrql.Bag"))
	    return new Node("bag",new Trees(new VariableLeaf("any")));
	if (cn.equals("hadoop.mrql.Inv"))
	    return new VariableLeaf("any");
	if (cn.equals("hadoop.mrql.Union"))
	    return new VariableLeaf("union");
	if (cn.equals("hadoop.mrql.Lambda"))
	    return new VariableLeaf("any");
	if (inf.length > 0 && inf[0].equals("hadoop.mrql.MRData"))
	    return new VariableLeaf("any");
	throw new Error("Unsupported type in imported method: "+cn);
    }

    static Trees signature ( Method m ) {
	Class<?> co = m.getReturnType();
	Class<?>[] cs = m.getParameterTypes();
	Trees as = new Trees(getType(co));
	for (int i = 0; i < cs.length; i++)
	    as = as.append(getType(cs[i]));
	return as;
    }

    public static String method_name ( int method_number ) {
	return methods.get(method_number).name;
    }

    public static Trees signature ( int method_number ) {
	return methods.get(method_number).signature;
    }

    public static void importClass ( String class_name ) {
	try {
	    Method[] ms = Class.forName(class_name).getMethods();
	    Vector<MethodInfo> mv = new Vector<MethodInfo>();
	    for (int i = 0; i < ms.length; i++)
		if (!object_method(ms[i].getName()) && ms[i].getModifiers() == 9)
		    try {
			Trees sig = signature(ms[i]);
			MethodInfo m = new MethodInfo(ms[i].getName(),sig,ms[i]);
			mv.add(m);
			methods.add(m);
			if (Translate.functions == null)
			    Translate.functions = Trees.nil;
			Translate.functions = Translate.functions.append(new Node(ms[i].getName(),sig));
		    } catch ( Exception e ) {

			System.out.println("Warning: method "+ms[i].getName()+" cannot be imported");
			System.out.println(e);
			throw new Error("");
		    };
	    Collections.sort(methods);
	    if (trace_imported_methods) {
		System.out.print("Importing methods: ");
		for (int i = 0; i < mv.size(); i++ )
		    System.out.print(mv.get(i).name+mv.get(i).signature.tail()
				     +":"+mv.get(i).signature.head()+"  ");
		System.out.println();
	    }
	} catch (ClassNotFoundException x) {
	    throw new Error("Undefined class: "+class_name);
	}
    }

    public static void importMethod ( String class_name, String method_name ) {
	try {
	    Method[] ms = Class.forName(class_name).getMethods();
	    MethodInfo m = null;
	    for (int i = 0; i < ms.length; i++)
		if (ms[i].getName().equals(method_name)
		    && !object_method(ms[i].getName()) && ms[i].getModifiers() == 9) {
		    Trees sig = signature(ms[i]);
		    m = new MethodInfo(ms[i].getName(),sig,ms[i]);
		    Translate.functions = Translate.functions.append(new Node(ms[i].getName(),sig));
		    break;
		};
	    if (m == null)
		throw new Error("No such method: "+method_name);
	    methods.add(m);
	    Collections.sort(methods);
	    if (trace_imported_methods)
		System.out.println("Importing method: "+m.name+m.signature.tail()
				   +":"+m.signature.head()+"  ");
	} catch (ClassNotFoundException x) {
	    throw new Error("Undefined class: "+class_name);
	}
    }

    public static Tree find_method ( String method_name, Trees args ) {
	for (int i = 0; i < methods.size(); i++ ) {
	    MethodInfo m = methods.get(i);
	    if (m.name.equals(method_name) && Translate.subtype(args,m.signature.tail()))
		return m.signature.head();
	};
	return null;
    }

    public static void print_methods () {
	for (int i = 0; i < methods.size(); i++ ) {
	    MethodInfo m = methods.get(i);
	    System.out.print(" "+m.name+":"+m.signature.tail()+"->"+m.signature.head());
	};
    }

    public static int find_method_number ( String method_name, Trees args ) {
	for (int i = 0; i < methods.size(); i++ ) {
	    MethodInfo m = methods.get(i);
	    if (m.name.equals(method_name) && Translate.subtype(args,m.signature.tail()))
		return i;
	};
	return -1;
    }

    public static MRData call ( int method_number, MRData... args ) {
	if (method_number < 0 || method_number >= methods.size())
	    throw new Error("Run-time error (unknown method name)");
	MethodInfo m = methods.get(method_number);
	try {
	    return (MRData)m.method.invoke(null,(Object[])args);
	} catch (Exception e) {
	    Tuple t = new Tuple(args.length);
	    for ( int i = 0; i < args.length; i++ )
		t.set(i,args[i]);
	    System.err.println("Run-time error in method call: "+m.name+t+" of type "
			       +m.signature.tail()+"->"+m.signature.head());
	    throw new Error(e.toString());
	}
    }
}
