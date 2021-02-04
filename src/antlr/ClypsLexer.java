// Generated from C:/Users/jeffc/Desktop/CMPILER-Machine-Project/src/antlr\Clyps.g4 by ANTLR 4.9.1
package antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ClypsLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, BOOLEAN=12, BREAK=13, BYTE=14, CHAR=15, CLASS=16, CONST=17, 
		DO=18, DOUBLE=19, ELSE=20, FINAL=21, FLOAT=22, FOR=23, IF=24, INT=25, 
		LONG=26, NEW=27, PRINT=28, RETURN=29, SCAN=30, SHORT=31, VOID=32, WHILE=33, 
		ArrayNum=34, IntegerLiteral=35, FloatingPointLiteral=36, BooleanLiteral=37, 
		CharacterLiteral=38, StringLiteral=39, NullLiteral=40, LPAREN=41, RPAREN=42, 
		LBRACE=43, RBRACE=44, LBRACK=45, RBRACK=46, SEMI=47, COMMA=48, DOT=49, 
		ASSIGN=50, GT=51, LT=52, BANG=53, TILDE=54, QUESTION=55, COLON=56, EQUAL=57, 
		LE=58, GE=59, NOTEQUAL=60, AND=61, OR=62, INC=63, DEC=64, ADD=65, SUB=66, 
		MUL=67, DIV=68, BITAND=69, BITOR=70, CARET=71, MOD=72, ARROW=73, COLONCOLON=74, 
		ADD_ASSIGN=75, SUB_ASSIGN=76, MUL_ASSIGN=77, DIV_ASSIGN=78, AND_ASSIGN=79, 
		OR_ASSIGN=80, XOR_ASSIGN=81, MOD_ASSIGN=82, LSHIFT_ASSIGN=83, RSHIFT_ASSIGN=84, 
		URSHIFT_ASSIGN=85, Identifier=86, AT=87, ELLIPSIS=88, WS=89, COMMENT=90, 
		LINE_COMMENT=91, ErrorCharacter=92;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "BOOLEAN", "BREAK", "BYTE", "CHAR", "CLASS", "CONST", 
			"DO", "DOUBLE", "ELSE", "FINAL", "FLOAT", "FOR", "IF", "INT", "LONG", 
			"NEW", "PRINT", "RETURN", "SCAN", "SHORT", "VOID", "WHILE", "ArrayNum", 
			"ArrayLiteral", "IntegerLiteral", "DecimalIntegerLiteral", "HexIntegerLiteral", 
			"OctalIntegerLiteral", "BinaryIntegerLiteral", "IntegerTypeSuffix", "DecimalNumeral", 
			"Digits", "Digit", "NonZeroDigit", "DigitsAndUnderscores", "DigitOrUnderscore", 
			"Underscores", "HexNumeral", "HexDigits", "HexDigit", "HexDigitsAndUnderscores", 
			"HexDigitOrUnderscore", "OctalNumeral", "OctalDigits", "OctalDigit", 
			"OctalDigitsAndUnderscores", "OctalDigitOrUnderscore", "BinaryNumeral", 
			"BinaryDigits", "BinaryDigit", "BinaryDigitsAndUnderscores", "BinaryDigitOrUnderscore", 
			"FloatingPointLiteral", "DecimalFloatingPointLiteral", "ExponentPart", 
			"ExponentIndicator", "SignedInteger", "Sign", "FloatTypeSuffix", "HexadecimalFloatingPointLiteral", 
			"HexSignificand", "BinaryExponent", "BinaryExponentIndicator", "BooleanLiteral", 
			"CharacterLiteral", "SingleCharacter", "StringLiteral", "StringCharacters", 
			"StringCharacter", "EscapeSequence", "OctalEscape", "ZeroToThree", "UnicodeEscape", 
			"NullLiteral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
			"SEMI", "COMMA", "DOT", "ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTION", 
			"COLON", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", 
			"ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", "MOD", "ARROW", 
			"COLONCOLON", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
			"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
			"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "IdentifierStart", "IdentifierPart", 
			"AT", "ELLIPSIS", "WS", "COMMENT", "LINE_COMMENT", "ErrorCharacter"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'main'", "'String'", "'this'", "'\"'", "'println'", "'up to'", 
			"'down to'", "'[ '", "'%%'", "'**'", "'//'", "'boolean'", "'break'", 
			"'byte'", "'char'", "'class'", "'const'", "'do'", "'double'", "'else'", 
			"'final'", "'float'", "'for'", "'if'", "'int'", "'long'", "'new'", "'print'", 
			"'return'", "'scan'", "'short'", "'void'", "'while'", null, null, null, 
			null, null, null, "'null'", "'('", "')'", "'{'", "'}'", "'['", "']'", 
			"';'", "','", "'.'", "'='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", 
			"'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'++'", "'--'", "'+'", 
			"'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", "'->'", "'::'", "'+='", 
			"'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", 
			"'>>>='", null, "'@'", "'...'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"BOOLEAN", "BREAK", "BYTE", "CHAR", "CLASS", "CONST", "DO", "DOUBLE", 
			"ELSE", "FINAL", "FLOAT", "FOR", "IF", "INT", "LONG", "NEW", "PRINT", 
			"RETURN", "SCAN", "SHORT", "VOID", "WHILE", "ArrayNum", "IntegerLiteral", 
			"FloatingPointLiteral", "BooleanLiteral", "CharacterLiteral", "StringLiteral", 
			"NullLiteral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
			"SEMI", "COMMA", "DOT", "ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTION", 
			"COLON", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", 
			"ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", "MOD", "ARROW", 
			"COLONCOLON", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
			"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
			"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "AT", "ELLIPSIS", "WS", 
			"COMMENT", "LINE_COMMENT", "ErrorCharacter"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ClypsLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Clyps.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2^\u038d\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv\4"+
		"w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t\u0080"+
		"\4\u0081\t\u0081\4\u0082\t\u0082\4\u0083\t\u0083\4\u0084\t\u0084\4\u0085"+
		"\t\u0085\4\u0086\t\u0086\4\u0087\t\u0087\4\u0088\t\u0088\4\u0089\t\u0089"+
		"\4\u008a\t\u008a\4\u008b\t\u008b\4\u008c\t\u008c\3\2\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25"+
		"\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3 "+
		"\3 \3 \3 \3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3$\3$\3$\7$\u01ca"+
		"\n$\f$\16$\u01cd\13$\3$\3$\3%\3%\3%\3%\5%\u01d5\n%\3&\3&\5&\u01d9\n&\3"+
		"\'\3\'\5\'\u01dd\n\'\3(\3(\5(\u01e1\n(\3)\3)\5)\u01e5\n)\3*\3*\3+\3+\3"+
		"+\5+\u01ec\n+\3+\3+\3+\5+\u01f1\n+\5+\u01f3\n+\3,\3,\5,\u01f7\n,\3,\5"+
		",\u01fa\n,\3-\3-\5-\u01fe\n-\3.\3.\3/\6/\u0203\n/\r/\16/\u0204\3\60\3"+
		"\60\5\60\u0209\n\60\3\61\6\61\u020c\n\61\r\61\16\61\u020d\3\62\3\62\3"+
		"\62\3\62\3\63\3\63\5\63\u0216\n\63\3\63\5\63\u0219\n\63\3\64\3\64\3\65"+
		"\6\65\u021e\n\65\r\65\16\65\u021f\3\66\3\66\5\66\u0224\n\66\3\67\3\67"+
		"\5\67\u0228\n\67\3\67\3\67\38\38\58\u022e\n8\38\58\u0231\n8\39\39\3:\6"+
		":\u0236\n:\r:\16:\u0237\3;\3;\5;\u023c\n;\3<\3<\3<\3<\3=\3=\5=\u0244\n"+
		"=\3=\5=\u0247\n=\3>\3>\3?\6?\u024c\n?\r?\16?\u024d\3@\3@\5@\u0252\n@\3"+
		"A\3A\5A\u0256\nA\3B\3B\3B\5B\u025b\nB\3B\5B\u025e\nB\3B\5B\u0261\nB\3"+
		"B\3B\3B\5B\u0266\nB\3B\5B\u0269\nB\3B\3B\3B\5B\u026e\nB\3B\3B\3B\5B\u0273"+
		"\nB\3C\3C\3C\3D\3D\3E\5E\u027b\nE\3E\3E\3F\3F\3G\3G\3H\3H\3H\5H\u0286"+
		"\nH\3I\3I\5I\u028a\nI\3I\3I\3I\5I\u028f\nI\3I\3I\5I\u0293\nI\3J\3J\3J"+
		"\3K\3K\3L\3L\3L\3L\3L\3L\3L\3L\3L\5L\u02a3\nL\3M\3M\3M\3M\3M\3M\3M\3M"+
		"\5M\u02ad\nM\3N\3N\3O\3O\5O\u02b3\nO\3O\3O\3P\6P\u02b8\nP\rP\16P\u02b9"+
		"\3Q\3Q\5Q\u02be\nQ\3R\3R\3R\3R\5R\u02c4\nR\3S\3S\3S\3S\3S\3S\3S\3S\3S"+
		"\3S\3S\5S\u02d1\nS\3T\3T\3U\3U\6U\u02d7\nU\rU\16U\u02d8\3U\3U\3U\3U\3"+
		"U\3V\3V\3V\3V\3V\3W\3W\3X\3X\3Y\3Y\3Z\3Z\3[\3[\3\\\3\\\3]\3]\3^\3^\3_"+
		"\3_\3`\3`\3a\3a\3b\3b\3c\3c\3d\3d\3e\3e\3f\3f\3g\3g\3g\3h\3h\3h\3i\3i"+
		"\3i\3j\3j\3j\3k\3k\3k\3l\3l\3l\3m\3m\3m\3n\3n\3n\3o\3o\3p\3p\3q\3q\3r"+
		"\3r\3s\3s\3t\3t\3u\3u\3v\3v\3w\3w\3w\3x\3x\3x\3y\3y\3y\3z\3z\3z\3{\3{"+
		"\3{\3|\3|\3|\3}\3}\3}\3~\3~\3~\3\177\3\177\3\177\3\u0080\3\u0080\3\u0080"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\3\u0082\3\u0082\3\u0082\3\u0082\3\u0083"+
		"\3\u0083\3\u0083\3\u0083\3\u0083\3\u0084\3\u0084\7\u0084\u035a\n\u0084"+
		"\f\u0084\16\u0084\u035d\13\u0084\3\u0085\5\u0085\u0360\n\u0085\3\u0086"+
		"\3\u0086\5\u0086\u0364\n\u0086\3\u0087\3\u0087\3\u0088\3\u0088\3\u0088"+
		"\3\u0088\3\u0089\6\u0089\u036d\n\u0089\r\u0089\16\u0089\u036e\3\u0089"+
		"\3\u0089\3\u008a\3\u008a\3\u008a\3\u008a\7\u008a\u0377\n\u008a\f\u008a"+
		"\16\u008a\u037a\13\u008a\3\u008a\3\u008a\3\u008a\3\u008a\3\u008a\3\u008b"+
		"\3\u008b\3\u008b\3\u008b\7\u008b\u0385\n\u008b\f\u008b\16\u008b\u0388"+
		"\13\u008b\3\u008b\3\u008b\3\u008c\3\u008c\3\u0378\2\u008d\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G"+
		"\2I%K\2M\2O\2Q\2S\2U\2W\2Y\2[\2]\2_\2a\2c\2e\2g\2i\2k\2m\2o\2q\2s\2u\2"+
		"w\2y\2{\2}\2\177\2\u0081&\u0083\2\u0085\2\u0087\2\u0089\2\u008b\2\u008d"+
		"\2\u008f\2\u0091\2\u0093\2\u0095\2\u0097\'\u0099(\u009b\2\u009d)\u009f"+
		"\2\u00a1\2\u00a3\2\u00a5\2\u00a7\2\u00a9\2\u00ab*\u00ad+\u00af,\u00b1"+
		"-\u00b3.\u00b5/\u00b7\60\u00b9\61\u00bb\62\u00bd\63\u00bf\64\u00c1\65"+
		"\u00c3\66\u00c5\67\u00c78\u00c99\u00cb:\u00cd;\u00cf<\u00d1=\u00d3>\u00d5"+
		"?\u00d7@\u00d9A\u00dbB\u00ddC\u00dfD\u00e1E\u00e3F\u00e5G\u00e7H\u00e9"+
		"I\u00ebJ\u00edK\u00efL\u00f1M\u00f3N\u00f5O\u00f7P\u00f9Q\u00fbR\u00fd"+
		"S\u00ffT\u0101U\u0103V\u0105W\u0107X\u0109\2\u010b\2\u010dY\u010fZ\u0111"+
		"[\u0113\\\u0115]\u0117^\3\2\25\4\2NNnn\3\2\63;\4\2ZZzz\5\2\62;CHch\3\2"+
		"\629\4\2DDdd\3\2\62\63\4\2GGgg\4\2--//\6\2FFHHffhh\4\2RRrr\6\2\f\f\17"+
		"\17))^^\6\2\f\f\17\17$$^^\n\2$$))^^ddhhppttvv\3\2\62\65\u0194\2&&C\\a"+
		"ac|\u00a4\u00a7\u00ac\u00ac\u00b7\u00b7\u00bc\u00bc\u00c2\u00d8\u00da"+
		"\u00f8\u00fa\u02c3\u02c8\u02d3\u02e2\u02e6\u02ee\u02ee\u02f0\u02f0\u0372"+
		"\u0376\u0378\u0379\u037c\u037f\u0381\u0381\u0388\u0388\u038a\u038c\u038e"+
		"\u038e\u0390\u03a3\u03a5\u03f7\u03f9\u0483\u048c\u0531\u0533\u0558\u055b"+
		"\u055b\u0563\u0589\u0591\u0591\u05d2\u05ec\u05f2\u05f4\u060d\u060d\u0622"+
		"\u064c\u0670\u0671\u0673\u06d5\u06d7\u06d7\u06e7\u06e8\u06f0\u06f1\u06fc"+
		"\u06fe\u0701\u0701\u0712\u0712\u0714\u0731\u074f\u07a7\u07b3\u07b3\u07cc"+
		"\u07ec\u07f6\u07f7\u07fc\u07fc\u0802\u0817\u081c\u081c\u0826\u0826\u082a"+
		"\u082a\u0842\u085a\u0862\u086c\u08a2\u08b6\u08b8\u08bf\u0906\u093b\u093f"+
		"\u093f\u0952\u0952\u095a\u0963\u0973\u0982\u0987\u098e\u0991\u0992\u0995"+
		"\u09aa\u09ac\u09b2\u09b4\u09b4\u09b8\u09bb\u09bf\u09bf\u09d0\u09d0\u09de"+
		"\u09df\u09e1\u09e3\u09f2\u09f5\u09fd\u09fe\u0a07\u0a0c\u0a11\u0a12\u0a15"+
		"\u0a2a\u0a2c\u0a32\u0a34\u0a35\u0a37\u0a38\u0a3a\u0a3b\u0a5b\u0a5e\u0a60"+
		"\u0a60\u0a74\u0a76\u0a87\u0a8f\u0a91\u0a93\u0a95\u0aaa\u0aac\u0ab2\u0ab4"+
		"\u0ab5\u0ab7\u0abb\u0abf\u0abf\u0ad2\u0ad2\u0ae2\u0ae3\u0af3\u0af3\u0afb"+
		"\u0afb\u0b07\u0b0e\u0b11\u0b12\u0b15\u0b2a\u0b2c\u0b32\u0b34\u0b35\u0b37"+
		"\u0b3b\u0b3f\u0b3f\u0b5e\u0b5f\u0b61\u0b63\u0b73\u0b73\u0b85\u0b85\u0b87"+
		"\u0b8c\u0b90\u0b92\u0b94\u0b97\u0b9b\u0b9c\u0b9e\u0b9e\u0ba0\u0ba1\u0ba5"+
		"\u0ba6\u0baa\u0bac\u0bb0\u0bbb\u0bd2\u0bd2\u0bfb\u0bfb\u0c07\u0c0e\u0c10"+
		"\u0c12\u0c14\u0c2a\u0c2c\u0c3b\u0c3f\u0c3f\u0c5a\u0c5c\u0c62\u0c63\u0c82"+
		"\u0c82\u0c87\u0c8e\u0c90\u0c92\u0c94\u0caa\u0cac\u0cb5\u0cb7\u0cbb\u0cbf"+
		"\u0cbf\u0ce0\u0ce0\u0ce2\u0ce3\u0cf3\u0cf4\u0d07\u0d0e\u0d10\u0d12\u0d14"+
		"\u0d3c\u0d3f\u0d3f\u0d50\u0d50\u0d56\u0d58\u0d61\u0d63\u0d7c\u0d81\u0d87"+
		"\u0d98\u0d9c\u0db3\u0db5\u0dbd\u0dbf\u0dbf\u0dc2\u0dc8\u0e03\u0e32\u0e34"+
		"\u0e35\u0e41\u0e48\u0e83\u0e84\u0e86\u0e86\u0e89\u0e8a\u0e8c\u0e8c\u0e8f"+
		"\u0e8f\u0e96\u0e99\u0e9b\u0ea1\u0ea3\u0ea5\u0ea7\u0ea7\u0ea9\u0ea9\u0eac"+
		"\u0ead\u0eaf\u0eb2\u0eb4\u0eb5\u0ebf\u0ebf\u0ec2\u0ec6\u0ec8\u0ec8\u0ede"+
		"\u0ee1\u0f02\u0f02\u0f42\u0f49\u0f4b\u0f6e\u0f8a\u0f8e\u1002\u102c\u1041"+
		"\u1041\u1052\u1057\u105c\u105f\u1063\u1063\u1067\u1068\u1070\u1072\u1077"+
		"\u1083\u1090\u1090\u10a2\u10c7\u10c9\u10c9\u10cf\u10cf\u10d2\u10fc\u10fe"+
		"\u124a\u124c\u124f\u1252\u1258\u125a\u125a\u125c\u125f\u1262\u128a\u128c"+
		"\u128f\u1292\u12b2\u12b4\u12b7\u12ba\u12c0\u12c2\u12c2\u12c4\u12c7\u12ca"+
		"\u12d8\u12da\u1312\u1314\u1317\u131a\u135c\u1382\u1391\u13a2\u13f7\u13fa"+
		"\u13ff\u1403\u166e\u1671\u1681\u1683\u169c\u16a2\u16ec\u16f0\u16fa\u1702"+
		"\u170e\u1710\u1713\u1722\u1733\u1742\u1753\u1762\u176e\u1770\u1772\u1782"+
		"\u17b5\u17d9\u17d9\u17dd\u17de\u1822\u1879\u1882\u1886\u1889\u18aa\u18ac"+
		"\u18ac\u18b2\u18f7\u1902\u1920\u1952\u196f\u1972\u1976\u1982\u19ad\u19b2"+
		"\u19cb\u1a02\u1a18\u1a22\u1a56\u1aa9\u1aa9\u1b07\u1b35\u1b47\u1b4d\u1b85"+
		"\u1ba2\u1bb0\u1bb1\u1bbc\u1be7\u1c02\u1c25\u1c4f\u1c51\u1c5c\u1c7f\u1c82"+
		"\u1c8a\u1ceb\u1cee\u1cf0\u1cf3\u1cf7\u1cf8\u1d02\u1dc1\u1e02\u1f17\u1f1a"+
		"\u1f1f\u1f22\u1f47\u1f4a\u1f4f\u1f52\u1f59\u1f5b\u1f5b\u1f5d\u1f5d\u1f5f"+
		"\u1f5f\u1f61\u1f7f\u1f82\u1fb6\u1fb8\u1fbe\u1fc0\u1fc0\u1fc4\u1fc6\u1fc8"+
		"\u1fce\u1fd2\u1fd5\u1fd8\u1fdd\u1fe2\u1fee\u1ff4\u1ff6\u1ff8\u1ffe\u2041"+
		"\u2042\u2056\u2056\u2073\u2073\u2081\u2081\u2092\u209e\u20a2\u20c1\u2104"+
		"\u2104\u2109\u2109\u210c\u2115\u2117\u2117\u211b\u211f\u2126\u2126\u2128"+
		"\u2128\u212a\u212a\u212c\u212f\u2131\u213b\u213e\u2141\u2147\u214b\u2150"+
		"\u2150\u2162\u218a\u2c02\u2c30\u2c32\u2c60\u2c62\u2ce6\u2ced\u2cf0\u2cf4"+
		"\u2cf5\u2d02\u2d27\u2d29\u2d29\u2d2f\u2d2f\u2d32\u2d69\u2d71\u2d71\u2d82"+
		"\u2d98\u2da2\u2da8\u2daa\u2db0\u2db2\u2db8\u2dba\u2dc0\u2dc2\u2dc8\u2dca"+
		"\u2dd0\u2dd2\u2dd8\u2dda\u2de0\u2e31\u2e31\u3007\u3009\u3023\u302b\u3033"+
		"\u3037\u303a\u303e\u3043\u3098\u309f\u30a1\u30a3\u30fc\u30fe\u3101\u3107"+
		"\u3130\u3133\u3190\u31a2\u31bc\u31f2\u3201\u3402\u4db7\u4e02\u9fec\ua002"+
		"\ua48e\ua4d2\ua4ff\ua502\ua60e\ua612\ua621\ua62c\ua62d\ua642\ua670\ua681"+
		"\ua69f\ua6a2\ua6f1\ua719\ua721\ua724\ua78a\ua78d\ua7b0\ua7b2\ua7b9\ua7f9"+
		"\ua803\ua805\ua807\ua809\ua80c\ua80e\ua824\ua83a\ua83a\ua842\ua875\ua884"+
		"\ua8b5\ua8f4\ua8f9\ua8fd\ua8fd\ua8ff\ua8ff\ua90c\ua927\ua932\ua948\ua962"+
		"\ua97e\ua986\ua9b4\ua9d1\ua9d1\ua9e2\ua9e6\ua9e8\ua9f1\ua9fc\uaa00\uaa02"+
		"\uaa2a\uaa42\uaa44\uaa46\uaa4d\uaa62\uaa78\uaa7c\uaa7c\uaa80\uaab1\uaab3"+
		"\uaab3\uaab7\uaab8\uaabb\uaabf\uaac2\uaac2\uaac4\uaac4\uaadd\uaadf\uaae2"+
		"\uaaec\uaaf4\uaaf6\uab03\uab08\uab0b\uab10\uab13\uab18\uab22\uab28\uab2a"+
		"\uab30\uab32\uab5c\uab5e\uab67\uab72\uabe4\uac02\ud7a5\ud7b2\ud7c8\ud7cd"+
		"\ud7fd\uf902\ufa6f\ufa72\ufadb\ufb02\ufb08\ufb15\ufb19\ufb1f\ufb1f\ufb21"+
		"\ufb2a\ufb2c\ufb38\ufb3a\ufb3e\ufb40\ufb40\ufb42\ufb43\ufb45\ufb46\ufb48"+
		"\ufbb3\ufbd5\ufd3f\ufd52\ufd91\ufd94\ufdc9\ufdf2\ufdfe\ufe35\ufe36\ufe4f"+
		"\ufe51\ufe6b\ufe6b\ufe72\ufe76\ufe78\ufefe\uff06\uff06\uff23\uff3c\uff41"+
		"\uff41\uff43\uff5c\uff68\uffc0\uffc4\uffc9\uffcc\uffd1\uffd4\uffd9\uffdc"+
		"\uffde\uffe2\uffe3\uffe7\uffe8\u00e6\2\62;\u0081\u00a1\u00af\u00af\u0302"+
		"\u0371\u0485\u0489\u0593\u05bf\u05c1\u05c1\u05c3\u05c4\u05c6\u05c7\u05c9"+
		"\u05c9\u0602\u0607\u0612\u061c\u061e\u061e\u064d\u066b\u0672\u0672\u06d8"+
		"\u06df\u06e1\u06e6\u06e9\u06ea\u06ec\u06ef\u06f2\u06fb\u0711\u0711\u0713"+
		"\u0713\u0732\u074c\u07a8\u07b2\u07c2\u07cb\u07ed\u07f5\u0818\u081b\u081d"+
		"\u0825\u0827\u0829\u082b\u082f\u085b\u085d\u08d6\u0905\u093c\u093e\u0940"+
		"\u0951\u0953\u0959\u0964\u0965\u0968\u0971\u0983\u0985\u09be\u09be\u09c0"+
		"\u09c6\u09c9\u09ca\u09cd\u09cf\u09d9\u09d9\u09e4\u09e5\u09e8\u09f1\u0a03"+
		"\u0a05\u0a3e\u0a3e\u0a40\u0a44\u0a49\u0a4a\u0a4d\u0a4f\u0a53\u0a53\u0a68"+
		"\u0a73\u0a77\u0a77\u0a83\u0a85\u0abe\u0abe\u0ac0\u0ac7\u0ac9\u0acb\u0acd"+
		"\u0acf\u0ae4\u0ae5\u0ae8\u0af1\u0afc\u0b01\u0b03\u0b05\u0b3e\u0b3e\u0b40"+
		"\u0b46\u0b49\u0b4a\u0b4d\u0b4f\u0b58\u0b59\u0b64\u0b65\u0b68\u0b71\u0b84"+
		"\u0b84\u0bc0\u0bc4\u0bc8\u0bca\u0bcc\u0bcf\u0bd9\u0bd9\u0be8\u0bf1\u0c02"+
		"\u0c05\u0c40\u0c46\u0c48\u0c4a\u0c4c\u0c4f\u0c57\u0c58\u0c64\u0c65\u0c68"+
		"\u0c71\u0c83\u0c85\u0cbe\u0cbe\u0cc0\u0cc6\u0cc8\u0cca\u0ccc\u0ccf\u0cd7"+
		"\u0cd8\u0ce4\u0ce5\u0ce8\u0cf1\u0d02\u0d05\u0d3d\u0d3e\u0d40\u0d46\u0d48"+
		"\u0d4a\u0d4c\u0d4f\u0d59\u0d59\u0d64\u0d65\u0d68\u0d71\u0d84\u0d85\u0dcc"+
		"\u0dcc\u0dd1\u0dd6\u0dd8\u0dd8\u0dda\u0de1\u0de8\u0df1\u0df4\u0df5\u0e33"+
		"\u0e33\u0e36\u0e3c\u0e49\u0e50\u0e52\u0e5b\u0eb3\u0eb3\u0eb6\u0ebb\u0ebd"+
		"\u0ebe\u0eca\u0ecf\u0ed2\u0edb\u0f1a\u0f1b\u0f22\u0f2b\u0f37\u0f37\u0f39"+
		"\u0f39\u0f3b\u0f3b\u0f40\u0f41\u0f73\u0f86\u0f88\u0f89\u0f8f\u0f99\u0f9b"+
		"\u0fbe\u0fc8\u0fc8\u102d\u1040\u1042\u104b\u1058\u105b\u1060\u1062\u1064"+
		"\u1066\u1069\u106f\u1073\u1076\u1084\u108f\u1091\u109f\u135f\u1361\u1714"+
		"\u1716\u1734\u1736\u1754\u1755\u1774\u1775\u17b6\u17d5\u17df\u17df\u17e2"+
		"\u17eb\u180d\u1810\u1812\u181b\u1887\u1888\u18ab\u18ab\u1922\u192d\u1932"+
		"\u193d\u1948\u1951\u19d2\u19db\u1a19\u1a1d\u1a57\u1a60\u1a62\u1a7e\u1a81"+
		"\u1a8b\u1a92\u1a9b\u1ab2\u1abf\u1b02\u1b06\u1b36\u1b46\u1b52\u1b5b\u1b6d"+
		"\u1b75\u1b82\u1b84\u1ba3\u1baf\u1bb2\u1bbb\u1be8\u1bf5\u1c26\u1c39\u1c42"+
		"\u1c4b\u1c52\u1c5b\u1cd2\u1cd4\u1cd6\u1cea\u1cef\u1cef\u1cf4\u1cf6\u1cf9"+
		"\u1cfb\u1dc2\u1dfb\u1dfd\u1e01\u200d\u2011\u202c\u2030\u2062\u2066\u2068"+
		"\u2071\u20d2\u20de\u20e3\u20e3\u20e7\u20f2\u2cf1\u2cf3\u2d81\u2d81\u2de2"+
		"\u2e01\u302c\u3031\u309b\u309c\ua622\ua62b\ua671\ua671\ua676\ua67f\ua6a0"+
		"\ua6a1\ua6f2\ua6f3\ua804\ua804\ua808\ua808\ua80d\ua80d\ua825\ua829\ua882"+
		"\ua883\ua8b6\ua8c7\ua8d2\ua8db\ua8e2\ua8f3\ua902\ua90b\ua928\ua92f\ua949"+
		"\ua955\ua982\ua985\ua9b5\ua9c2\ua9d2\ua9db\ua9e7\ua9e7\ua9f2\ua9fb\uaa2b"+
		"\uaa38\uaa45\uaa45\uaa4e\uaa4f\uaa52\uaa5b\uaa7d\uaa7f\uaab2\uaab2\uaab4"+
		"\uaab6\uaab9\uaaba\uaac0\uaac1\uaac3\uaac3\uaaed\uaaf1\uaaf7\uaaf8\uabe5"+
		"\uabec\uabee\uabef\uabf2\uabfb\ufb20\ufb20\ufe02\ufe11\ufe22\ufe31\uff01"+
		"\uff01\uff12\uff1b\ufffb\ufffd\5\2\13\f\17\17\"\"\4\2\f\f\17\17\2\u0399"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2I\3\2\2"+
		"\2\2\u0081\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2\2\2\u009d\3\2\2\2\2\u00ab"+
		"\3\2\2\2\2\u00ad\3\2\2\2\2\u00af\3\2\2\2\2\u00b1\3\2\2\2\2\u00b3\3\2\2"+
		"\2\2\u00b5\3\2\2\2\2\u00b7\3\2\2\2\2\u00b9\3\2\2\2\2\u00bb\3\2\2\2\2\u00bd"+
		"\3\2\2\2\2\u00bf\3\2\2\2\2\u00c1\3\2\2\2\2\u00c3\3\2\2\2\2\u00c5\3\2\2"+
		"\2\2\u00c7\3\2\2\2\2\u00c9\3\2\2\2\2\u00cb\3\2\2\2\2\u00cd\3\2\2\2\2\u00cf"+
		"\3\2\2\2\2\u00d1\3\2\2\2\2\u00d3\3\2\2\2\2\u00d5\3\2\2\2\2\u00d7\3\2\2"+
		"\2\2\u00d9\3\2\2\2\2\u00db\3\2\2\2\2\u00dd\3\2\2\2\2\u00df\3\2\2\2\2\u00e1"+
		"\3\2\2\2\2\u00e3\3\2\2\2\2\u00e5\3\2\2\2\2\u00e7\3\2\2\2\2\u00e9\3\2\2"+
		"\2\2\u00eb\3\2\2\2\2\u00ed\3\2\2\2\2\u00ef\3\2\2\2\2\u00f1\3\2\2\2\2\u00f3"+
		"\3\2\2\2\2\u00f5\3\2\2\2\2\u00f7\3\2\2\2\2\u00f9\3\2\2\2\2\u00fb\3\2\2"+
		"\2\2\u00fd\3\2\2\2\2\u00ff\3\2\2\2\2\u0101\3\2\2\2\2\u0103\3\2\2\2\2\u0105"+
		"\3\2\2\2\2\u0107\3\2\2\2\2\u010d\3\2\2\2\2\u010f\3\2\2\2\2\u0111\3\2\2"+
		"\2\2\u0113\3\2\2\2\2\u0115\3\2\2\2\2\u0117\3\2\2\2\3\u0119\3\2\2\2\5\u011e"+
		"\3\2\2\2\7\u0125\3\2\2\2\t\u012a\3\2\2\2\13\u012c\3\2\2\2\r\u0134\3\2"+
		"\2\2\17\u013a\3\2\2\2\21\u0142\3\2\2\2\23\u0145\3\2\2\2\25\u0148\3\2\2"+
		"\2\27\u014b\3\2\2\2\31\u014e\3\2\2\2\33\u0156\3\2\2\2\35\u015c\3\2\2\2"+
		"\37\u0161\3\2\2\2!\u0166\3\2\2\2#\u016c\3\2\2\2%\u0172\3\2\2\2\'\u0175"+
		"\3\2\2\2)\u017c\3\2\2\2+\u0181\3\2\2\2-\u0187\3\2\2\2/\u018d\3\2\2\2\61"+
		"\u0191\3\2\2\2\63\u0194\3\2\2\2\65\u0198\3\2\2\2\67\u019d\3\2\2\29\u01a1"+
		"\3\2\2\2;\u01a7\3\2\2\2=\u01ae\3\2\2\2?\u01b3\3\2\2\2A\u01b9\3\2\2\2C"+
		"\u01be\3\2\2\2E\u01c4\3\2\2\2G\u01c6\3\2\2\2I\u01d4\3\2\2\2K\u01d6\3\2"+
		"\2\2M\u01da\3\2\2\2O\u01de\3\2\2\2Q\u01e2\3\2\2\2S\u01e6\3\2\2\2U\u01f2"+
		"\3\2\2\2W\u01f4\3\2\2\2Y\u01fd\3\2\2\2[\u01ff\3\2\2\2]\u0202\3\2\2\2_"+
		"\u0208\3\2\2\2a\u020b\3\2\2\2c\u020f\3\2\2\2e\u0213\3\2\2\2g\u021a\3\2"+
		"\2\2i\u021d\3\2\2\2k\u0223\3\2\2\2m\u0225\3\2\2\2o\u022b\3\2\2\2q\u0232"+
		"\3\2\2\2s\u0235\3\2\2\2u\u023b\3\2\2\2w\u023d\3\2\2\2y\u0241\3\2\2\2{"+
		"\u0248\3\2\2\2}\u024b\3\2\2\2\177\u0251\3\2\2\2\u0081\u0255\3\2\2\2\u0083"+
		"\u0272\3\2\2\2\u0085\u0274\3\2\2\2\u0087\u0277\3\2\2\2\u0089\u027a\3\2"+
		"\2\2\u008b\u027e\3\2\2\2\u008d\u0280\3\2\2\2\u008f\u0282\3\2\2\2\u0091"+
		"\u0292\3\2\2\2\u0093\u0294\3\2\2\2\u0095\u0297\3\2\2\2\u0097\u02a2\3\2"+
		"\2\2\u0099\u02ac\3\2\2\2\u009b\u02ae\3\2\2\2\u009d\u02b0\3\2\2\2\u009f"+
		"\u02b7\3\2\2\2\u00a1\u02bd\3\2\2\2\u00a3\u02c3\3\2\2\2\u00a5\u02d0\3\2"+
		"\2\2\u00a7\u02d2\3\2\2\2\u00a9\u02d4\3\2\2\2\u00ab\u02df\3\2\2\2\u00ad"+
		"\u02e4\3\2\2\2\u00af\u02e6\3\2\2\2\u00b1\u02e8\3\2\2\2\u00b3\u02ea\3\2"+
		"\2\2\u00b5\u02ec\3\2\2\2\u00b7\u02ee\3\2\2\2\u00b9\u02f0\3\2\2\2\u00bb"+
		"\u02f2\3\2\2\2\u00bd\u02f4\3\2\2\2\u00bf\u02f6\3\2\2\2\u00c1\u02f8\3\2"+
		"\2\2\u00c3\u02fa\3\2\2\2\u00c5\u02fc\3\2\2\2\u00c7\u02fe\3\2\2\2\u00c9"+
		"\u0300\3\2\2\2\u00cb\u0302\3\2\2\2\u00cd\u0304\3\2\2\2\u00cf\u0307\3\2"+
		"\2\2\u00d1\u030a\3\2\2\2\u00d3\u030d\3\2\2\2\u00d5\u0310\3\2\2\2\u00d7"+
		"\u0313\3\2\2\2\u00d9\u0316\3\2\2\2\u00db\u0319\3\2\2\2\u00dd\u031c\3\2"+
		"\2\2\u00df\u031e\3\2\2\2\u00e1\u0320\3\2\2\2\u00e3\u0322\3\2\2\2\u00e5"+
		"\u0324\3\2\2\2\u00e7\u0326\3\2\2\2\u00e9\u0328\3\2\2\2\u00eb\u032a\3\2"+
		"\2\2\u00ed\u032c\3\2\2\2\u00ef\u032f\3\2\2\2\u00f1\u0332\3\2\2\2\u00f3"+
		"\u0335\3\2\2\2\u00f5\u0338\3\2\2\2\u00f7\u033b\3\2\2\2\u00f9\u033e\3\2"+
		"\2\2\u00fb\u0341\3\2\2\2\u00fd\u0344\3\2\2\2\u00ff\u0347\3\2\2\2\u0101"+
		"\u034a\3\2\2\2\u0103\u034e\3\2\2\2\u0105\u0352\3\2\2\2\u0107\u0357\3\2"+
		"\2\2\u0109\u035f\3\2\2\2\u010b\u0363\3\2\2\2\u010d\u0365\3\2\2\2\u010f"+
		"\u0367\3\2\2\2\u0111\u036c\3\2\2\2\u0113\u0372\3\2\2\2\u0115\u0380\3\2"+
		"\2\2\u0117\u038b\3\2\2\2\u0119\u011a\7o\2\2\u011a\u011b\7c\2\2\u011b\u011c"+
		"\7k\2\2\u011c\u011d\7p\2\2\u011d\4\3\2\2\2\u011e\u011f\7U\2\2\u011f\u0120"+
		"\7v\2\2\u0120\u0121\7t\2\2\u0121\u0122\7k\2\2\u0122\u0123\7p\2\2\u0123"+
		"\u0124\7i\2\2\u0124\6\3\2\2\2\u0125\u0126\7v\2\2\u0126\u0127\7j\2\2\u0127"+
		"\u0128\7k\2\2\u0128\u0129\7u\2\2\u0129\b\3\2\2\2\u012a\u012b\7$\2\2\u012b"+
		"\n\3\2\2\2\u012c\u012d\7r\2\2\u012d\u012e\7t\2\2\u012e\u012f\7k\2\2\u012f"+
		"\u0130\7p\2\2\u0130\u0131\7v\2\2\u0131\u0132\7n\2\2\u0132\u0133\7p\2\2"+
		"\u0133\f\3\2\2\2\u0134\u0135\7w\2\2\u0135\u0136\7r\2\2\u0136\u0137\7\""+
		"\2\2\u0137\u0138\7v\2\2\u0138\u0139\7q\2\2\u0139\16\3\2\2\2\u013a\u013b"+
		"\7f\2\2\u013b\u013c\7q\2\2\u013c\u013d\7y\2\2\u013d\u013e\7p\2\2\u013e"+
		"\u013f\7\"\2\2\u013f\u0140\7v\2\2\u0140\u0141\7q\2\2\u0141\20\3\2\2\2"+
		"\u0142\u0143\7]\2\2\u0143\u0144\7\"\2\2\u0144\22\3\2\2\2\u0145\u0146\7"+
		"\'\2\2\u0146\u0147\7\'\2\2\u0147\24\3\2\2\2\u0148\u0149\7,\2\2\u0149\u014a"+
		"\7,\2\2\u014a\26\3\2\2\2\u014b\u014c\7\61\2\2\u014c\u014d\7\61\2\2\u014d"+
		"\30\3\2\2\2\u014e\u014f\7d\2\2\u014f\u0150\7q\2\2\u0150\u0151\7q\2\2\u0151"+
		"\u0152\7n\2\2\u0152\u0153\7g\2\2\u0153\u0154\7c\2\2\u0154\u0155\7p\2\2"+
		"\u0155\32\3\2\2\2\u0156\u0157\7d\2\2\u0157\u0158\7t\2\2\u0158\u0159\7"+
		"g\2\2\u0159\u015a\7c\2\2\u015a\u015b\7m\2\2\u015b\34\3\2\2\2\u015c\u015d"+
		"\7d\2\2\u015d\u015e\7{\2\2\u015e\u015f\7v\2\2\u015f\u0160\7g\2\2\u0160"+
		"\36\3\2\2\2\u0161\u0162\7e\2\2\u0162\u0163\7j\2\2\u0163\u0164\7c\2\2\u0164"+
		"\u0165\7t\2\2\u0165 \3\2\2\2\u0166\u0167\7e\2\2\u0167\u0168\7n\2\2\u0168"+
		"\u0169\7c\2\2\u0169\u016a\7u\2\2\u016a\u016b\7u\2\2\u016b\"\3\2\2\2\u016c"+
		"\u016d\7e\2\2\u016d\u016e\7q\2\2\u016e\u016f\7p\2\2\u016f\u0170\7u\2\2"+
		"\u0170\u0171\7v\2\2\u0171$\3\2\2\2\u0172\u0173\7f\2\2\u0173\u0174\7q\2"+
		"\2\u0174&\3\2\2\2\u0175\u0176\7f\2\2\u0176\u0177\7q\2\2\u0177\u0178\7"+
		"w\2\2\u0178\u0179\7d\2\2\u0179\u017a\7n\2\2\u017a\u017b\7g\2\2\u017b("+
		"\3\2\2\2\u017c\u017d\7g\2\2\u017d\u017e\7n\2\2\u017e\u017f\7u\2\2\u017f"+
		"\u0180\7g\2\2\u0180*\3\2\2\2\u0181\u0182\7h\2\2\u0182\u0183\7k\2\2\u0183"+
		"\u0184\7p\2\2\u0184\u0185\7c\2\2\u0185\u0186\7n\2\2\u0186,\3\2\2\2\u0187"+
		"\u0188\7h\2\2\u0188\u0189\7n\2\2\u0189\u018a\7q\2\2\u018a\u018b\7c\2\2"+
		"\u018b\u018c\7v\2\2\u018c.\3\2\2\2\u018d\u018e\7h\2\2\u018e\u018f\7q\2"+
		"\2\u018f\u0190\7t\2\2\u0190\60\3\2\2\2\u0191\u0192\7k\2\2\u0192\u0193"+
		"\7h\2\2\u0193\62\3\2\2\2\u0194\u0195\7k\2\2\u0195\u0196\7p\2\2\u0196\u0197"+
		"\7v\2\2\u0197\64\3\2\2\2\u0198\u0199\7n\2\2\u0199\u019a\7q\2\2\u019a\u019b"+
		"\7p\2\2\u019b\u019c\7i\2\2\u019c\66\3\2\2\2\u019d\u019e\7p\2\2\u019e\u019f"+
		"\7g\2\2\u019f\u01a0\7y\2\2\u01a08\3\2\2\2\u01a1\u01a2\7r\2\2\u01a2\u01a3"+
		"\7t\2\2\u01a3\u01a4\7k\2\2\u01a4\u01a5\7p\2\2\u01a5\u01a6\7v\2\2\u01a6"+
		":\3\2\2\2\u01a7\u01a8\7t\2\2\u01a8\u01a9\7g\2\2\u01a9\u01aa\7v\2\2\u01aa"+
		"\u01ab\7w\2\2\u01ab\u01ac\7t\2\2\u01ac\u01ad\7p\2\2\u01ad<\3\2\2\2\u01ae"+
		"\u01af\7u\2\2\u01af\u01b0\7e\2\2\u01b0\u01b1\7c\2\2\u01b1\u01b2\7p\2\2"+
		"\u01b2>\3\2\2\2\u01b3\u01b4\7u\2\2\u01b4\u01b5\7j\2\2\u01b5\u01b6\7q\2"+
		"\2\u01b6\u01b7\7t\2\2\u01b7\u01b8\7v\2\2\u01b8@\3\2\2\2\u01b9\u01ba\7"+
		"x\2\2\u01ba\u01bb\7q\2\2\u01bb\u01bc\7k\2\2\u01bc\u01bd\7f\2\2\u01bdB"+
		"\3\2\2\2\u01be\u01bf\7y\2\2\u01bf\u01c0\7j\2\2\u01c0\u01c1\7k\2\2\u01c1"+
		"\u01c2\7n\2\2\u01c2\u01c3\7g\2\2\u01c3D\3\2\2\2\u01c4\u01c5\5G$\2\u01c5"+
		"F\3\2\2\2\u01c6\u01c7\7]\2\2\u01c7\u01cb\4\63;\2\u01c8\u01ca\4\62;\2\u01c9"+
		"\u01c8\3\2\2\2\u01ca\u01cd\3\2\2\2\u01cb\u01c9\3\2\2\2\u01cb\u01cc\3\2"+
		"\2\2\u01cc\u01ce\3\2\2\2\u01cd\u01cb\3\2\2\2\u01ce\u01cf\7_\2\2\u01cf"+
		"H\3\2\2\2\u01d0\u01d5\5K&\2\u01d1\u01d5\5M\'\2\u01d2\u01d5\5O(\2\u01d3"+
		"\u01d5\5Q)\2\u01d4\u01d0\3\2\2\2\u01d4\u01d1\3\2\2\2\u01d4\u01d2\3\2\2"+
		"\2\u01d4\u01d3\3\2\2\2\u01d5J\3\2\2\2\u01d6\u01d8\5U+\2\u01d7\u01d9\5"+
		"S*\2\u01d8\u01d7\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9L\3\2\2\2\u01da\u01dc"+
		"\5c\62\2\u01db\u01dd\5S*\2\u01dc\u01db\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd"+
		"N\3\2\2\2\u01de\u01e0\5m\67\2\u01df\u01e1\5S*\2\u01e0\u01df\3\2\2\2\u01e0"+
		"\u01e1\3\2\2\2\u01e1P\3\2\2\2\u01e2\u01e4\5w<\2\u01e3\u01e5\5S*\2\u01e4"+
		"\u01e3\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e5R\3\2\2\2\u01e6\u01e7\t\2\2\2"+
		"\u01e7T\3\2\2\2\u01e8\u01f3\7\62\2\2\u01e9\u01f0\5[.\2\u01ea\u01ec\5W"+
		",\2\u01eb\u01ea\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01f1\3\2\2\2\u01ed"+
		"\u01ee\5a\61\2\u01ee\u01ef\5W,\2\u01ef\u01f1\3\2\2\2\u01f0\u01eb\3\2\2"+
		"\2\u01f0\u01ed\3\2\2\2\u01f1\u01f3\3\2\2\2\u01f2\u01e8\3\2\2\2\u01f2\u01e9"+
		"\3\2\2\2\u01f3V\3\2\2\2\u01f4\u01f9\5Y-\2\u01f5\u01f7\5]/\2\u01f6\u01f5"+
		"\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7\u01f8\3\2\2\2\u01f8\u01fa\5Y-\2\u01f9"+
		"\u01f6\3\2\2\2\u01f9\u01fa\3\2\2\2\u01faX\3\2\2\2\u01fb\u01fe\7\62\2\2"+
		"\u01fc\u01fe\5[.\2\u01fd\u01fb\3\2\2\2\u01fd\u01fc\3\2\2\2\u01feZ\3\2"+
		"\2\2\u01ff\u0200\t\3\2\2\u0200\\\3\2\2\2\u0201\u0203\5_\60\2\u0202\u0201"+
		"\3\2\2\2\u0203\u0204\3\2\2\2\u0204\u0202\3\2\2\2\u0204\u0205\3\2\2\2\u0205"+
		"^\3\2\2\2\u0206\u0209\5Y-\2\u0207\u0209\7a\2\2\u0208\u0206\3\2\2\2\u0208"+
		"\u0207\3\2\2\2\u0209`\3\2\2\2\u020a\u020c\7a\2\2\u020b\u020a\3\2\2\2\u020c"+
		"\u020d\3\2\2\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2\2\2\u020eb\3\2\2\2"+
		"\u020f\u0210\7\62\2\2\u0210\u0211\t\4\2\2\u0211\u0212\5e\63\2\u0212d\3"+
		"\2\2\2\u0213\u0218\5g\64\2\u0214\u0216\5i\65\2\u0215\u0214\3\2\2\2\u0215"+
		"\u0216\3\2\2\2\u0216\u0217\3\2\2\2\u0217\u0219\5g\64\2\u0218\u0215\3\2"+
		"\2\2\u0218\u0219\3\2\2\2\u0219f\3\2\2\2\u021a\u021b\t\5\2\2\u021bh\3\2"+
		"\2\2\u021c\u021e\5k\66\2\u021d\u021c\3\2\2\2\u021e\u021f\3\2\2\2\u021f"+
		"\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220j\3\2\2\2\u0221\u0224\5g\64\2"+
		"\u0222\u0224\7a\2\2\u0223\u0221\3\2\2\2\u0223\u0222\3\2\2\2\u0224l\3\2"+
		"\2\2\u0225\u0227\7\62\2\2\u0226\u0228\5a\61\2\u0227\u0226\3\2\2\2\u0227"+
		"\u0228\3\2\2\2\u0228\u0229\3\2\2\2\u0229\u022a\5o8\2\u022an\3\2\2\2\u022b"+
		"\u0230\5q9\2\u022c\u022e\5s:\2\u022d\u022c\3\2\2\2\u022d\u022e\3\2\2\2"+
		"\u022e\u022f\3\2\2\2\u022f\u0231\5q9\2\u0230\u022d\3\2\2\2\u0230\u0231"+
		"\3\2\2\2\u0231p\3\2\2\2\u0232\u0233\t\6\2\2\u0233r\3\2\2\2\u0234\u0236"+
		"\5u;\2\u0235\u0234\3\2\2\2\u0236\u0237\3\2\2\2\u0237\u0235\3\2\2\2\u0237"+
		"\u0238\3\2\2\2\u0238t\3\2\2\2\u0239\u023c\5q9\2\u023a\u023c\7a\2\2\u023b"+
		"\u0239\3\2\2\2\u023b\u023a\3\2\2\2\u023cv\3\2\2\2\u023d\u023e\7\62\2\2"+
		"\u023e\u023f\t\7\2\2\u023f\u0240\5y=\2\u0240x\3\2\2\2\u0241\u0246\5{>"+
		"\2\u0242\u0244\5}?\2\u0243\u0242\3\2\2\2\u0243\u0244\3\2\2\2\u0244\u0245"+
		"\3\2\2\2\u0245\u0247\5{>\2\u0246\u0243\3\2\2\2\u0246\u0247\3\2\2\2\u0247"+
		"z\3\2\2\2\u0248\u0249\t\b\2\2\u0249|\3\2\2\2\u024a\u024c\5\177@\2\u024b"+
		"\u024a\3\2\2\2\u024c\u024d\3\2\2\2\u024d\u024b\3\2\2\2\u024d\u024e\3\2"+
		"\2\2\u024e~\3\2\2\2\u024f\u0252\5{>\2\u0250\u0252\7a\2\2\u0251\u024f\3"+
		"\2\2\2\u0251\u0250\3\2\2\2\u0252\u0080\3\2\2\2\u0253\u0256\5\u0083B\2"+
		"\u0254\u0256\5\u008fH\2\u0255\u0253\3\2\2\2\u0255\u0254\3\2\2\2\u0256"+
		"\u0082\3\2\2\2\u0257\u0258\5W,\2\u0258\u025a\7\60\2\2\u0259\u025b\5W,"+
		"\2\u025a\u0259\3\2\2\2\u025a\u025b\3\2\2\2\u025b\u025d\3\2\2\2\u025c\u025e"+
		"\5\u0085C\2\u025d\u025c\3\2\2\2\u025d\u025e\3\2\2\2\u025e\u0260\3\2\2"+
		"\2\u025f\u0261\5\u008dG\2\u0260\u025f\3\2\2\2\u0260\u0261\3\2\2\2\u0261"+
		"\u0273\3\2\2\2\u0262\u0263\7\60\2\2\u0263\u0265\5W,\2\u0264\u0266\5\u0085"+
		"C\2\u0265\u0264\3\2\2\2\u0265\u0266\3\2\2\2\u0266\u0268\3\2\2\2\u0267"+
		"\u0269\5\u008dG\2\u0268\u0267\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u0273"+
		"\3\2\2\2\u026a\u026b\5W,\2\u026b\u026d\5\u0085C\2\u026c\u026e\5\u008d"+
		"G\2\u026d\u026c\3\2\2\2\u026d\u026e\3\2\2\2\u026e\u0273\3\2\2\2\u026f"+
		"\u0270\5W,\2\u0270\u0271\5\u008dG\2\u0271\u0273\3\2\2\2\u0272\u0257\3"+
		"\2\2\2\u0272\u0262\3\2\2\2\u0272\u026a\3\2\2\2\u0272\u026f\3\2\2\2\u0273"+
		"\u0084\3\2\2\2\u0274\u0275\5\u0087D\2\u0275\u0276\5\u0089E\2\u0276\u0086"+
		"\3\2\2\2\u0277\u0278\t\t\2\2\u0278\u0088\3\2\2\2\u0279\u027b\5\u008bF"+
		"\2\u027a\u0279\3\2\2\2\u027a\u027b\3\2\2\2\u027b\u027c\3\2\2\2\u027c\u027d"+
		"\5W,\2\u027d\u008a\3\2\2\2\u027e\u027f\t\n\2\2\u027f\u008c\3\2\2\2\u0280"+
		"\u0281\t\13\2\2\u0281\u008e\3\2\2\2\u0282\u0283\5\u0091I\2\u0283\u0285"+
		"\5\u0093J\2\u0284\u0286\5\u008dG\2\u0285\u0284\3\2\2\2\u0285\u0286\3\2"+
		"\2\2\u0286\u0090\3\2\2\2\u0287\u0289\5c\62\2\u0288\u028a\7\60\2\2\u0289"+
		"\u0288\3\2\2\2\u0289\u028a\3\2\2\2\u028a\u0293\3\2\2\2\u028b\u028c\7\62"+
		"\2\2\u028c\u028e\t\4\2\2\u028d\u028f\5e\63\2\u028e\u028d\3\2\2\2\u028e"+
		"\u028f\3\2\2\2\u028f\u0290\3\2\2\2\u0290\u0291\7\60\2\2\u0291\u0293\5"+
		"e\63\2\u0292\u0287\3\2\2\2\u0292\u028b\3\2\2\2\u0293\u0092\3\2\2\2\u0294"+
		"\u0295\5\u0095K\2\u0295\u0296\5\u0089E\2\u0296\u0094\3\2\2\2\u0297\u0298"+
		"\t\f\2\2\u0298\u0096\3\2\2\2\u0299\u029a\7v\2\2\u029a\u029b\7t\2\2\u029b"+
		"\u029c\7w\2\2\u029c\u02a3\7g\2\2\u029d\u029e\7h\2\2\u029e\u029f\7c\2\2"+
		"\u029f\u02a0\7n\2\2\u02a0\u02a1\7u\2\2\u02a1\u02a3\7g\2\2\u02a2\u0299"+
		"\3\2\2\2\u02a2\u029d\3\2\2\2\u02a3\u0098\3\2\2\2\u02a4\u02a5\7)\2\2\u02a5"+
		"\u02a6\5\u009bN\2\u02a6\u02a7\7)\2\2\u02a7\u02ad\3\2\2\2\u02a8\u02a9\7"+
		")\2\2\u02a9\u02aa\5\u00a3R\2\u02aa\u02ab\7)\2\2\u02ab\u02ad\3\2\2\2\u02ac"+
		"\u02a4\3\2\2\2\u02ac\u02a8\3\2\2\2\u02ad\u009a\3\2\2\2\u02ae\u02af\n\r"+
		"\2\2\u02af\u009c\3\2\2\2\u02b0\u02b2\7$\2\2\u02b1\u02b3\5\u009fP\2\u02b2"+
		"\u02b1\3\2\2\2\u02b2\u02b3\3\2\2\2\u02b3\u02b4\3\2\2\2\u02b4\u02b5\7$"+
		"\2\2\u02b5\u009e\3\2\2\2\u02b6\u02b8\5\u00a1Q\2\u02b7\u02b6\3\2\2\2\u02b8"+
		"\u02b9\3\2\2\2\u02b9\u02b7\3\2\2\2\u02b9\u02ba\3\2\2\2\u02ba\u00a0\3\2"+
		"\2\2\u02bb\u02be\n\16\2\2\u02bc\u02be\5\u00a3R\2\u02bd\u02bb\3\2\2\2\u02bd"+
		"\u02bc\3\2\2\2\u02be\u00a2\3\2\2\2\u02bf\u02c0\7^\2\2\u02c0\u02c4\t\17"+
		"\2\2\u02c1\u02c4\5\u00a5S\2\u02c2\u02c4\5\u00a9U\2\u02c3\u02bf\3\2\2\2"+
		"\u02c3\u02c1\3\2\2\2\u02c3\u02c2\3\2\2\2\u02c4\u00a4\3\2\2\2\u02c5\u02c6"+
		"\7^\2\2\u02c6\u02d1\5q9\2\u02c7\u02c8\7^\2\2\u02c8\u02c9\5q9\2\u02c9\u02ca"+
		"\5q9\2\u02ca\u02d1\3\2\2\2\u02cb\u02cc\7^\2\2\u02cc\u02cd\5\u00a7T\2\u02cd"+
		"\u02ce\5q9\2\u02ce\u02cf\5q9\2\u02cf\u02d1\3\2\2\2\u02d0\u02c5\3\2\2\2"+
		"\u02d0\u02c7\3\2\2\2\u02d0\u02cb\3\2\2\2\u02d1\u00a6\3\2\2\2\u02d2\u02d3"+
		"\t\20\2\2\u02d3\u00a8\3\2\2\2\u02d4\u02d6\7^\2\2\u02d5\u02d7\7w\2\2\u02d6"+
		"\u02d5\3\2\2\2\u02d7\u02d8\3\2\2\2\u02d8\u02d6\3\2\2\2\u02d8\u02d9\3\2"+
		"\2\2\u02d9\u02da\3\2\2\2\u02da\u02db\5g\64\2\u02db\u02dc\5g\64\2\u02dc"+
		"\u02dd\5g\64\2\u02dd\u02de\5g\64\2\u02de\u00aa\3\2\2\2\u02df\u02e0\7p"+
		"\2\2\u02e0\u02e1\7w\2\2\u02e1\u02e2\7n\2\2\u02e2\u02e3\7n\2\2\u02e3\u00ac"+
		"\3\2\2\2\u02e4\u02e5\7*\2\2\u02e5\u00ae\3\2\2\2\u02e6\u02e7\7+\2\2\u02e7"+
		"\u00b0\3\2\2\2\u02e8\u02e9\7}\2\2\u02e9\u00b2\3\2\2\2\u02ea\u02eb\7\177"+
		"\2\2\u02eb\u00b4\3\2\2\2\u02ec\u02ed\7]\2\2\u02ed\u00b6\3\2\2\2\u02ee"+
		"\u02ef\7_\2\2\u02ef\u00b8\3\2\2\2\u02f0\u02f1\7=\2\2\u02f1\u00ba\3\2\2"+
		"\2\u02f2\u02f3\7.\2\2\u02f3\u00bc\3\2\2\2\u02f4\u02f5\7\60\2\2\u02f5\u00be"+
		"\3\2\2\2\u02f6\u02f7\7?\2\2\u02f7\u00c0\3\2\2\2\u02f8\u02f9\7@\2\2\u02f9"+
		"\u00c2\3\2\2\2\u02fa\u02fb\7>\2\2\u02fb\u00c4\3\2\2\2\u02fc\u02fd\7#\2"+
		"\2\u02fd\u00c6\3\2\2\2\u02fe\u02ff\7\u0080\2\2\u02ff\u00c8\3\2\2\2\u0300"+
		"\u0301\7A\2\2\u0301\u00ca\3\2\2\2\u0302\u0303\7<\2\2\u0303\u00cc\3\2\2"+
		"\2\u0304\u0305\7?\2\2\u0305\u0306\7?\2\2\u0306\u00ce\3\2\2\2\u0307\u0308"+
		"\7>\2\2\u0308\u0309\7?\2\2\u0309\u00d0\3\2\2\2\u030a\u030b\7@\2\2\u030b"+
		"\u030c\7?\2\2\u030c\u00d2\3\2\2\2\u030d\u030e\7#\2\2\u030e\u030f\7?\2"+
		"\2\u030f\u00d4\3\2\2\2\u0310\u0311\7(\2\2\u0311\u0312\7(\2\2\u0312\u00d6"+
		"\3\2\2\2\u0313\u0314\7~\2\2\u0314\u0315\7~\2\2\u0315\u00d8\3\2\2\2\u0316"+
		"\u0317\7-\2\2\u0317\u0318\7-\2\2\u0318\u00da\3\2\2\2\u0319\u031a\7/\2"+
		"\2\u031a\u031b\7/\2\2\u031b\u00dc\3\2\2\2\u031c\u031d\7-\2\2\u031d\u00de"+
		"\3\2\2\2\u031e\u031f\7/\2\2\u031f\u00e0\3\2\2\2\u0320\u0321\7,\2\2\u0321"+
		"\u00e2\3\2\2\2\u0322\u0323\7\61\2\2\u0323\u00e4\3\2\2\2\u0324\u0325\7"+
		"(\2\2\u0325\u00e6\3\2\2\2\u0326\u0327\7~\2\2\u0327\u00e8\3\2\2\2\u0328"+
		"\u0329\7`\2\2\u0329\u00ea\3\2\2\2\u032a\u032b\7\'\2\2\u032b\u00ec\3\2"+
		"\2\2\u032c\u032d\7/\2\2\u032d\u032e\7@\2\2\u032e\u00ee\3\2\2\2\u032f\u0330"+
		"\7<\2\2\u0330\u0331\7<\2\2\u0331\u00f0\3\2\2\2\u0332\u0333\7-\2\2\u0333"+
		"\u0334\7?\2\2\u0334\u00f2\3\2\2\2\u0335\u0336\7/\2\2\u0336\u0337\7?\2"+
		"\2\u0337\u00f4\3\2\2\2\u0338\u0339\7,\2\2\u0339\u033a\7?\2\2\u033a\u00f6"+
		"\3\2\2\2\u033b\u033c\7\61\2\2\u033c\u033d\7?\2\2\u033d\u00f8\3\2\2\2\u033e"+
		"\u033f\7(\2\2\u033f\u0340\7?\2\2\u0340\u00fa\3\2\2\2\u0341\u0342\7~\2"+
		"\2\u0342\u0343\7?\2\2\u0343\u00fc\3\2\2\2\u0344\u0345\7`\2\2\u0345\u0346"+
		"\7?\2\2\u0346\u00fe\3\2\2\2\u0347\u0348\7\'\2\2\u0348\u0349\7?\2\2\u0349"+
		"\u0100\3\2\2\2\u034a\u034b\7>\2\2\u034b\u034c\7>\2\2\u034c\u034d\7?\2"+
		"\2\u034d\u0102\3\2\2\2\u034e\u034f\7@\2\2\u034f\u0350\7@\2\2\u0350\u0351"+
		"\7?\2\2\u0351\u0104\3\2\2\2\u0352\u0353\7@\2\2\u0353\u0354\7@\2\2\u0354"+
		"\u0355\7@\2\2\u0355\u0356\7?\2\2\u0356\u0106\3\2\2\2\u0357\u035b\5\u0109"+
		"\u0085\2\u0358\u035a\5\u010b\u0086\2\u0359\u0358\3\2\2\2\u035a\u035d\3"+
		"\2\2\2\u035b\u0359\3\2\2\2\u035b\u035c\3\2\2\2\u035c\u0108\3\2\2\2\u035d"+
		"\u035b\3\2\2\2\u035e\u0360\t\21\2\2\u035f\u035e\3\2\2\2\u0360\u010a\3"+
		"\2\2\2\u0361\u0364\5\u0109\u0085\2\u0362\u0364\t\22\2\2\u0363\u0361\3"+
		"\2\2\2\u0363\u0362\3\2\2\2\u0364\u010c\3\2\2\2\u0365\u0366\7B\2\2\u0366"+
		"\u010e\3\2\2\2\u0367\u0368\7\60\2\2\u0368\u0369\7\60\2\2\u0369\u036a\7"+
		"\60\2\2\u036a\u0110\3\2\2\2\u036b\u036d\t\23\2\2\u036c\u036b\3\2\2\2\u036d"+
		"\u036e\3\2\2\2\u036e\u036c\3\2\2\2\u036e\u036f\3\2\2\2\u036f\u0370\3\2"+
		"\2\2\u0370\u0371\b\u0089\2\2\u0371\u0112\3\2\2\2\u0372\u0373\7\61\2\2"+
		"\u0373\u0374\7,\2\2\u0374\u0378\3\2\2\2\u0375\u0377\13\2\2\2\u0376\u0375"+
		"\3\2\2\2\u0377\u037a\3\2\2\2\u0378\u0379\3\2\2\2\u0378\u0376\3\2\2\2\u0379"+
		"\u037b\3\2\2\2\u037a\u0378\3\2\2\2\u037b\u037c\7,\2\2\u037c\u037d\7\61"+
		"\2\2\u037d\u037e\3\2\2\2\u037e\u037f\b\u008a\2\2\u037f\u0114\3\2\2\2\u0380"+
		"\u0381\7\61\2\2\u0381\u0382\7\61\2\2\u0382\u0386\3\2\2\2\u0383\u0385\n"+
		"\24\2\2\u0384\u0383\3\2\2\2\u0385\u0388\3\2\2\2\u0386\u0384\3\2\2\2\u0386"+
		"\u0387\3\2\2\2\u0387\u0389\3\2\2\2\u0388\u0386\3\2\2\2\u0389\u038a\b\u008b"+
		"\2\2\u038a\u0116\3\2\2\2\u038b\u038c\13\2\2\2\u038c\u0118\3\2\2\2:\2\u01cb"+
		"\u01d4\u01d8\u01dc\u01e0\u01e4\u01eb\u01f0\u01f2\u01f6\u01f9\u01fd\u0204"+
		"\u0208\u020d\u0215\u0218\u021f\u0223\u0227\u022d\u0230\u0237\u023b\u0243"+
		"\u0246\u024d\u0251\u0255\u025a\u025d\u0260\u0265\u0268\u026d\u0272\u027a"+
		"\u0285\u0289\u028e\u0292\u02a2\u02ac\u02b2\u02b9\u02bd\u02c3\u02d0\u02d8"+
		"\u035b\u035f\u0363\u036e\u0378\u0386\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}