package com.bubblechess.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
   ServerHandlerTest.class,
   MoveValidationTest.class,
   StateRecognitionTest.class,
   ServerTest.class
})
public class BubbleChessTestSuite {
	
}
