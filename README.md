### Dev

Prep dev

This app uses greensock and will require a .npmrc to be created at the project root with the following code. Get the token from greensock member area

```
always-auth=true
//npm.greensock.com/:_authToken=AUTH_TOKEN
@gsap:registry=https://npm.greensock.com
```

```bash
npm i
```

Start dev

```bash
npm run dev
```

Jack in via calva, shadow-cljs -> :app

Fire up http://localhost:4200/

### Deployment

Build the release package

```bash
npm run release
```

Log into the correct netlify account

```
netlify sites:list
```

Make sure the atd site is listed

```
npm run release
netlify deploy --prod
```

Until netlify updates clojure, build manualy
