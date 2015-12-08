<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='UTF-8' indent='yes'/>

<xsl:template match="descript">
	<xsl:apply-templates/>
</xsl:template>


<xsl:template match="para0">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="subpara1">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="subpara2">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="subpara3">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="subpara4">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="para0/title">
	<div class="para0_title">
		<xsl:number count="para0"/>
		<xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text>
		<xsl:value-of select="."/>
	</div>
</xsl:template>

<xsl:template match="subpara1/title">
	<div class="subpara1_title">
		<xsl:number count="para0"/>
		<xsl:text>.</xsl:text>
		<xsl:number count="subpara1"/>
		<xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text>
		<xsl:value-of select="."/>
	</div>
</xsl:template>

<xsl:template match="subpara2/title">
	<div class="subpara2_title">
		<xsl:number count="para0"/>
		<xsl:text>.</xsl:text>
		<xsl:number count="subpara1"/>
		<xsl:text>.</xsl:text>
		<xsl:number count="subpara2"/>
		<xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text>
		<xsl:value-of select="."/>
	</div>
</xsl:template>

<xsl:template match="para">
	<p class="para_firstline_indent_text"><xsl:value-of select="."/></p>
</xsl:template>

<xsl:template match="figure">
	<xsl:variable name="icn"><xsl:value-of select="graphic/@boardno" /></xsl:variable>
	<div class="figure">
    	<p align="center"><img class="figure_min" id="{$icn}.png" src="thumbnails/min_{$icn}.GIF" /></p>
    </div>
	<div class="figure_multimedia_icn"><xsl:value-of select="$icn"/></div>
    <div class="figure_title" >
    	<a name="dir-title-5"></a>
    	<xsl:text>图片</xsl:text>
    	<xsl:number count="//figure"/>
    	<xsl:text> </xsl:text>
		<xsl:value-of select="title"/>
    </div>
</xsl:template>


<xsl:template match="multimedia">
	<xsl:variable name="icn"><xsl:value-of select="multimediaobject/@boardno" /></xsl:variable>
	<p align="center"><img class="figure_min" onclick="" style="width:48px;border-width:2px;border-style:solid;" name="Media.gif" id="{$icn}.mp4" src="manual-resources/images/media.png" alt="多媒体1  视频一"/></p>
	<div class="figure_multimedia_icn"><xsl:value-of select="$icn"/></div>
    <div class="multimedia_title" >
    	<xsl:text>多媒体</xsl:text>
    	<xsl:number count="//multimedia"/>
    	<xsl:text> </xsl:text>
		<xsl:value-of select="title"/>
    </div>
</xsl:template>


</xsl:stylesheet>
