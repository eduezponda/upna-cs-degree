%{
    #include "lista_nombres.h"
    #include "parser.tab.h"
    #include "definiciones.h"
%}

%option yylineno

P_LETRA           [a-zA-Z]
P_DIGITO          [0-9]
P_ID_BOOLEANO     (?i:b)_({P_LETRA}|{P_DIGITO})*
P_ID              {P_LETRA}({P_LETRA}|{P_DIGITO})*
P_BLANCO_O_SALTO  [ \t\n]
P_EB              {P_BLANCO_O_SALTO}+
P_COMENTARIO      \{[^}\n]*\}
P_SUMA	        \+
P_RESTA  	    \- 
P_MULTIPLICACION \*
P_DIVISION_ENTERA       (?i:div)
P_DIVISION_REAL         \/
P_RESTO	        (?i:mod)
P_COMPARADOR_MENOR          <
P_COMPARADOR_MAYOR          >
P_COMPARADOR_MAYOR_IGUAL    >=
P_COMPARADOR_MENOR_IGUAL    <=
P_COMPARADOR_DISTINTO       <>
P_COMPARADOR_IGUAL         =
P_OPERADOR_Y     (?i:y)
P_OPERADOR_O     (?i:o)
P_FIN_INSTRUCCION \;
P_COMA          ,
P_THEN          ->
P_ASIGNACION    :=
P_DEL_TIPO      :
P_PARENTESIS_I  \(
P_PARENTESIS_D  \)
P_CORCHETE_I    \[
P_CORCHETE_D    \]
P_VALOR_BOOL_TRUE   (?i:verdadero)
P_VALOR_BOOL_FALSE  (?i:falso)
P_VALOR_CADENA  \"[^\"]*\"
P_VALOR_CARACTER \'[^\"]\'
P_COMILLA       \"|\'
P_ALGORITMO     (?i:algoritmo)
P_CONTINUAR     (?i:continuar)
P_MIENTRAS      (?i:mientras)
P_PARA          (?i:para)
P_SI            (?i:si)
P_SINO          \[\]
P_VALORES_ENTRADA (?i:ent)
P_VALORES_ENTRADA_SALIDA (?i:e\/s)
P_VALORES_SALIDA (?i:sal)
P_HACER         (?i:hacer)
P_HASTA         (?i:hasta)
P_NEGACION      (?i:no)
P_VAR           (?i:var)
P_BOOLEANO      (?i:booleano)
P_CADENA        (?i:cadena)
P_CARACTER      (?i:caracter)
P_ENTERO        (?i:entero)
P_REAL          (?i:real)
P_CONST         (?i:const)

P_FALGORITMO    (?i:falgoritmo)
P_FCONST        (?i:fconst)
P_FMIENTRAS     (?i:fmientras)
P_FPARA         (?i:fpara)
P_FSI           (?i:fsi)
P_FVAR          (?i:fvar)

%%

{P_DIGITO}+\.{P_DIGITO}?(E[\+\-]?{P_DIGITO})* 	{yylval.valor.tipo = TIPO_REAL; yylval.valor.real = atof(yytext); return VALOR;}
{P_DIGITO}+ 				{yylval.valor.tipo = TIPO_ENTERO; yylval.valor.entero = atoi(yytext); return VALOR;}
{P_VALOR_BOOL_TRUE}         {yylval.valor.tipo = TIPO_BOOL; yylval.valor.cadena = "true"; return VALOR_BOOL;}
{P_VALOR_BOOL_FALSE}        {yylval.valor.tipo = TIPO_BOOL; yylval.valor.cadena = "false"; return VALOR_BOOL;}
{P_VALOR_CADENA}            {
                                yylval.valor.tipo = TIPO_CADENA;
                                yylval.valor.cadena = strdup(yytext + 1);
                                yylval.valor.cadena[strlen(yylval.valor.cadena) - 1] = '\0';
                                return VALOR;
                            }
{P_VALOR_CARACTER}          {yylval.valor.tipo = TIPO_CARACTER; yylval.valor.caracter = yytext[1]; return VALOR;}

{P_SUMA}	        {return SUMA;}
{P_RESTA}	        {return RESTA;}
{P_MULTIPLICACION}	    {return MULTIPLICACION;}
{P_DIVISION_ENTERA} 	{yylval.tipo = TIPO_DIVISION_ENTERA; return DIVISION;}
{P_DIVISION_REAL} 		{yylval.tipo = TIPO_DIVISION_REAL; return DIVISION;}
{P_RESTO} 		        {return RESTO;}
{P_OPERADOR_Y}		    {yylval.tipo = TIPO_OPERADOR_Y; return OPERADOR;}
{P_OPERADOR_O}		    {yylval.tipo = TIPO_OPERADOR_0; return OPERADOR;}
{P_COMPARADOR_MENOR}	        {yylval.tipo = TIPO_COMPARADOR_MENOR; return COMPARADOR;}
{P_COMPARADOR_MAYOR}	        {yylval.tipo = TIPO_COMPARADOR_MAYOR; return COMPARADOR;}
{P_COMPARADOR_MENOR_IGUAL}	    {yylval.tipo = TIPO_COMPARADOR_MENOR_IGUAL; return COMPARADOR;}
{P_COMPARADOR_MAYOR_IGUAL}	    {yylval.tipo = TIPO_COMPARADOR_MAYOR_IGUAL; return COMPARADOR;}
{P_COMPARADOR_DISTINTO}	        {yylval.tipo = TIPO_COMPARADOR_DISTINTO; return COMPARADOR;}
{P_COMPARADOR_IGUAL}               {yylval.tipo = TIPO_COMPARADOR_IGUAL; return COMPARADOR;}

{P_ALGORITMO}           {return ALGORITMO;}
{P_SI}			        {return SI;}
{P_SINO}                {return SINO;}
{P_THEN}		        {return THEN;}
{P_CONTINUAR}           {return CONTINUAR;}
{P_MIENTRAS}            {return MIENTRAS;}
{P_PARA}                {return PARA;}

{P_ASIGNACION}          {return ASIGNACION;}
{P_DEL_TIPO}            {return DEL_TIPO;}
{P_PARENTESIS_D}        {return PARENTESIS_D;}
{P_PARENTESIS_I}        {return PARENTESIS_I;}
{P_CORCHETE_I}          {return CORCHETE_I;}
{P_CORCHETE_D}          {return CORCHETE_D;}

{P_HACER}               {return HACER;}
{P_HASTA}               {return HASTA;}
{P_NEGACION}            {return NEGACION;}
{P_VAR}                 {return VAR;}
{P_BOOLEANO}            {yylval.tipo=TIPO_BOOL; return BOOLEANO;}
{P_CADENA}              {yylval.tipo=TIPO_CADENA; return CADENA;}
{P_CARACTER}            {yylval.tipo=TIPO_CARACTER; return CARACTER;}
{P_ENTERO}              {yylval.tipo=TIPO_ENTERO; return ENTERO;}
{P_REAL}                {yylval.tipo=TIPO_REAL; return REAL;}

{P_FALGORITMO}          {return FALGORITMO;}
{P_FMIENTRAS}           {return FMIENTRAS;}
{P_FPARA}               {return FPARA;}
{P_FSI}                 {return FSI;}
{P_FVAR}                {return FVAR;}

{P_VALORES_ENTRADA}     {return VALORES_ENTRADA;}
{P_VALORES_ENTRADA_SALIDA} {return VALORES_ENTRADA_SALIDA;}
{P_VALORES_SALIDA}      {return VALORES_SALIDA;}

{P_ID_BOOLEANO}           {yylval.nombre = strdup(yytext); return ID_BOOLEANO;}
{P_ID}                    {yylval.nombre = strdup(yytext); return ID;}

{P_COMA}                {return COMA;}
{P_FIN_INSTRUCCION}     {return FIN_INSTRUCCION;}
{P_COMENTARIO}            ;
{P_EB}                    ;

%%

int yywrap(void) {
    return 1;
}
