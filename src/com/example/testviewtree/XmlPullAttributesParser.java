/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.testviewtree;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import com.example.testviewtree.engine.XmlUtils;
import android.content.res.XmlResourceParser;


/**
 * Provides an implementation of AttributeSet on top of an XmlPullParser.
 */
class XmlPullAttributesParser implements XmlResourceParser {
    public XmlPullAttributesParser(XmlPullParser parser) {
        mParser = parser;
        
    }

    //属性总数
    public int getAttributeCount() {
        return mParser.getAttributeCount();
    }
    //属性名
    public String getAttributeName(int index) {
        return mParser.getAttributeName(index);
    }
    //属性值
    public String getAttributeValue(int index) {
        return mParser.getAttributeValue(index);
    }

    public String getAttributeValue(String namespace, String name) {
        return mParser.getAttributeValue(namespace, name);
    }

    public String getPositionDescription() {
        return mParser.getPositionDescription();
    }

    public int getAttributeNameResource(int index) {
        return 0;
    }

    public int getAttributeListValue(String namespace, String attribute,
            String[] options, int defaultValue) {
        return XmlUtils.convertValueToList(
            getAttributeValue(namespace, attribute), options, defaultValue);
    }

    public boolean getAttributeBooleanValue(String namespace, String attribute,
            boolean defaultValue) {
        return XmlUtils.convertValueToBoolean(
            getAttributeValue(namespace, attribute), defaultValue);
    }

    public int getAttributeResourceValue(String namespace, String attribute,
            int defaultValue) {
        return XmlUtils.convertValueToInt(
            getAttributeValue(namespace, attribute), defaultValue);
    }

    public int getAttributeIntValue(String namespace, String attribute,
            int defaultValue) {
        return XmlUtils.convertValueToInt(
            getAttributeValue(namespace, attribute), defaultValue);
    }

    public int getAttributeUnsignedIntValue(String namespace, String attribute,
            int defaultValue) {
        return XmlUtils.convertValueToUnsignedInt(
            getAttributeValue(namespace, attribute), defaultValue);
    }

    public float getAttributeFloatValue(String namespace, String attribute,
            float defaultValue) {
        String s = getAttributeValue(namespace, attribute);
        if (s != null) {
            return Float.parseFloat(s);
        }
        return defaultValue;
    }

    public int getAttributeListValue(int index,
            String[] options, int defaultValue) {
        return XmlUtils.convertValueToList(
            getAttributeValue(index), options, defaultValue);
    }

    public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
        return XmlUtils.convertValueToBoolean(
            getAttributeValue(index), defaultValue);
    }

    public int getAttributeResourceValue(int index, int defaultValue) {
        return XmlUtils.convertValueToInt(
            getAttributeValue(index), defaultValue);
    }

    public int getAttributeIntValue(int index, int defaultValue) {
        return XmlUtils.convertValueToInt(
            getAttributeValue(index), defaultValue);
    }

    public int getAttributeUnsignedIntValue(int index, int defaultValue) {
        return XmlUtils.convertValueToUnsignedInt(
            getAttributeValue(index), defaultValue);
    }

    public float getAttributeFloatValue(int index, float defaultValue) {
        String s = getAttributeValue(index);
        if (s != null) {
            return Float.parseFloat(s);
        }
        return defaultValue;
    }

    public String getIdAttribute() {
        return getAttributeValue(null, "id");
    }

    public String getClassAttribute() {
        return getAttributeValue(null, "class");
    }

    public int getIdAttributeResourceValue(int defaultValue) {
        return getAttributeResourceValue(null, "id", defaultValue);
    }

    public int getStyleAttribute() {
        return getAttributeResourceValue(null, "style", 0);
    }

    private XmlPullParser mParser;

	@Override
	public void setFeature(String name, boolean state)
			throws XmlPullParserException {
		 if (FEATURE_PROCESS_NAMESPACES.equals(name) && state) {
	            return;
	        }
	        if (FEATURE_REPORT_NAMESPACE_ATTRIBUTES.equals(name) && state) {
	            return;
	        }
	        throw new XmlPullParserException("Unsupported feature: " + name);
	}

	@Override
	public boolean getFeature(String name) {
		if (FEATURE_PROCESS_NAMESPACES.equals(name)) {
            return true;
        }
        if (FEATURE_REPORT_NAMESPACE_ATTRIBUTES.equals(name)) {
            return true;
        }
        return false;
	}

	@Override
	public void setProperty(String name, Object value)
			throws XmlPullParserException {
		 throw new XmlPullParserException("setProperty() not supported");
		
	}

	@Override
	public Object getProperty(String name) {
		return null;
	}

	@Override
	public void setInput(Reader in) throws XmlPullParserException {
		mParser.setInput(in);
	}

	@Override
	public void setInput(InputStream inputStream, String inputEncoding)
			throws XmlPullParserException {
		 mParser.setInput(inputStream, inputEncoding);
	}

	@Override
	public String getInputEncoding() {
		return null;
	}

	@Override
	public void defineEntityReplacementText(String entityName,
			String replacementText) throws XmlPullParserException {
		 throw new XmlPullParserException(
	                "defineEntityReplacementText() not supported");
	    }

	@Override
	public int getNamespaceCount(int depth) throws XmlPullParserException {
		 throw new XmlPullParserException("getNamespaceCount() not supported");
    }



	@Override
	public String getNamespacePrefix(int pos) throws XmlPullParserException {
		 throw new XmlPullParserException("getNamespacePrefix() not supported");
    }
	@Override
	public String getNamespaceUri(int pos) throws XmlPullParserException {
		 throw new XmlPullParserException("getNamespaceUri() not supported");
    }

	@Override
	public String getNamespace(String prefix) {
		return mParser.getNamespace();
	}

	@Override
	public int getDepth() {
		
		 return mParser.getDepth();
	}

	@Override
	public int getLineNumber() {
		return mParser.getLineNumber();
	}

	@Override
	public int getColumnNumber() {
		
		return -1;
	}

	@Override
	public boolean isWhitespace() throws XmlPullParserException {
		 return mParser.isWhitespace();
	}

	@Override
	public String getText() {
		 return mParser.getText();
	}

	@Override
	public char[] getTextCharacters(int[] holderForStartAndLength) {
		 String txt = getText();
	        char[] chars = null;
	        if (txt != null) {
	            holderForStartAndLength[0] = 0;
	            holderForStartAndLength[1] = txt.length();
	            chars = new char[txt.length()];
	            txt.getChars(0, txt.length(), chars, 0);
	        }
	        return chars;
	}

	@Override
	public String getNamespace() {
		 return mParser.getNamespace();
	}

	@Override
	public String getName() {
		 return mParser.getName();
	}

	@Override
	public String getPrefix() {
		 throw new RuntimeException("getPrefix not supported");
    }

	@Override
	public boolean isEmptyElementTag() throws XmlPullParserException {
		return false;
	}

	@Override
	public String getAttributeNamespace(int index) {
		 return mParser.getAttributeNamespace(index);
    }

	@Override
	public String getAttributePrefix(int index) {
		   throw new RuntimeException("getAttributePrefix not supported");
    }

	@Override
	public String getAttributeType(int index) {
		 return "CDATA";
	}

	@Override
	public boolean isAttributeDefault(int index) {
		
		return false;
	}
	 private int mEventType = START_DOCUMENT;
	@Override
	public int getEventType() throws XmlPullParserException {
		 return mEventType;
	}
	  private boolean mStarted = false;
	@Override
	public int next() throws XmlPullParserException, IOException {
		if (!mStarted) {
            mStarted = true;

            /*if (ParserFactory.LOG_PARSER) {
                System.out.println("STRT " + mParser.toString());
            }*/

            return START_DOCUMENT;
        }

        int ev = mParser.next();

       /* if (ParserFactory.LOG_PARSER) {
            System.out.println("NEXT " + mParser.toString() + " " +
                    eventTypeToString(mEventType) + " -> " + eventTypeToString(ev));
        }

        if (ev == END_TAG && mParser.getDepth() == 1) {
            // done with parser remove it from the context stack.
//            ensurePopped();

            if (ParserFactory.LOG_PARSER) {
                System.out.println("");
            }
        }*/

        mEventType = ev;
        return ev;
	}

	@Override
	public int nextToken() throws XmlPullParserException, IOException {
		 return next();
	}

	@Override
	public void require(int type, String namespace, String name)
			throws XmlPullParserException, IOException {
		 if (type != getEventType()
	                || (namespace != null && !namespace.equals(getNamespace()))
	                || (name != null && !name.equals(getName())))
	            throw new XmlPullParserException("expected " + TYPES[type]
	                    + getPositionDescription());
	}

	@Override
	public String nextText() throws XmlPullParserException, IOException {
		if (getEventType() != START_TAG) {
            throw new XmlPullParserException(getPositionDescription()
                    + ": parser must be on START_TAG to read next text", this,
                    null);
        }
        int eventType = next();
        if (eventType == TEXT) {
            String result = getText();
            eventType = next();
            if (eventType != END_TAG) {
                throw new XmlPullParserException(
                        getPositionDescription()
                                + ": event TEXT it must be immediately followed by END_TAG",
                        this, null);
            }
            return result;
        } else if (eventType == END_TAG) {
            return "";
        } else {
            throw new XmlPullParserException(getPositionDescription()
                    + ": parser must be on START_TAG or TEXT to read text",
                    this, null);
        }
	}

	@Override
	public int nextTag() throws XmlPullParserException, IOException {
		 int eventType = next();
	        if (eventType == TEXT && isWhitespace()) { // skip whitespace
	            eventType = next();
	        }
	        if (eventType != START_TAG && eventType != END_TAG) {
	            throw new XmlPullParserException(getPositionDescription()
	                    + ": expected start or end tag", this, null);
	        }
	        return eventType;
	}

	@Override
	public void close() {

	}
}
