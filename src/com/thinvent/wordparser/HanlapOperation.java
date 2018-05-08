package com.thinvent.wordparser;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

public class HanlapOperation
{
    public List<String> keyWordExtractOperation(String inputContext, int KeyWordnumber)
    {

        List<String> keywordList = HanLP.extractKeyword(inputContext, KeyWordnumber);
        return keywordList;
    }

    public List<Term> getwordList(String inputContext)
    {
        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
        List<Term> list = segment.seg(inputContext);
        return list;
    }
}
