# SIP_metaploter beta version

	Usage:
	
		simple <loopsFile> <RawData> <script> <sMetaPlot> <sImg> [-min MIN] [-max MAX] [-resMax TRUE/FALSE][-z] [-s] [-c COLORSCHEME]
		substraction <loopsFile> <RawData1> <RawData2> <script> <sMetaPlot> <sImg> [-min MIN] [-max MAX] [-resMax TRUE/FALSE][-z] [-s] [-c COLORSCHEME]
		
		Param:
			sMetaPlot: size of the metaplot (default 20 bins)
			sImg: size of the image analysed by SIP (default 2000 bins)
			-resMax TRUE or FALSE: default true, if false take the samller resolution
			-c COLORSCHEME  matplotlib_colors (Blues, BuGn, Greens, Purples, Reds, coolwarm, magma, inferno, spectral, viridis) default Reds
			-z znorm each ring in bullseye
			-s Trim edges to make a square
			-min MIN minvalue for color scale
			-max Max maxvalue for color scale
			-h, --help print help
