#include "zoomjoystrong.h"
#include <math.h>
#include <SDL2/SDL.h>

struct color current;

void setup(){
	window = SDL_CreateWindow
		(
		 "ZoomJoyString", SDL_WINDOWPOS_UNDEFINED,
		 SDL_WINDOWPOS_UNDEFINED,
		 WIDTH,
		 HEIGHT,
		 SDL_WINDOW_ALLOW_HIGHDPI | SDL_WINDOW_SHOWN
		);

	renderer =  SDL_CreateRenderer( window, -1, SDL_RENDERER_ACCELERATED);
	SDL_SetRenderDrawBlendMode(renderer, SDL_BLENDMODE_BLEND);
	texture = SDL_CreateTexture(renderer, SDL_PIXELFORMAT_RGBA8888, SDL_TEXTUREACCESS_TARGET, WIDTH, HEIGHT);
	SDL_SetRenderTarget(renderer, texture);
	SDL_RenderSetScale( renderer, 3.0, 3.0 );
	
	SDL_SetRenderDrawColor( renderer, 255, 255, 255, 255 );
	SDL_RenderClear( renderer );
	SDL_SetRenderDrawColor( renderer, 0, 0, 0, 255);	
	current.r = 0;
	current.b = 0;
	current.g = 0;
}

void set_color( int r, int g, int b){
	current.r = r;
	current.b = g;
	current.g = b;
	SDL_SetRenderDrawColor( renderer, r, g, b, 255);
}

void point( int x, int y ){
	SDL_SetRenderTarget(renderer, texture);
	SDL_RenderDrawPoint(renderer, x, y);	
	SDL_SetRenderTarget(renderer, NULL);
	SDL_RenderCopy(renderer, texture, NULL, NULL);
	SDL_RenderPresent(renderer);
}

void line( int x1, int y1, int x2, int y2 ){
	SDL_SetRenderTarget(renderer, texture);
	SDL_RenderDrawLine(renderer, x1, y1, x2, y2);
	SDL_SetRenderTarget(renderer, NULL);
	SDL_RenderCopy(renderer, texture, NULL, NULL);
	SDL_RenderPresent(renderer);
}

void circle(int x, int y, int r){
	for(float i=0; i<2 * 3.14; i+=.01){
		float u = x + r * cos(i);
		float v = y + r * sin(i);
		point(u, v);
	}
} 

void rectangle(int x, int y, int w, int h){
	SDL_Rect rect;
	rect.x = x;
	rect.y = y;
	rect.w = w;
	rect.h = h;
	SDL_SetRenderTarget(renderer, texture);
        SDL_RenderDrawRect(renderer, &rect);
        SDL_SetRenderTarget(renderer, NULL);
        SDL_RenderCopy(renderer, texture, NULL, NULL);
        SDL_RenderPresent(renderer);
}

void finish(){
	SDL_Delay(5000);
	SDL_DestroyWindow(window);
	SDL_Quit();
}
