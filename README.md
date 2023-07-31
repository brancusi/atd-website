### Dev

Start dev

```bash
npm run dev
```

Jack in via calva, shadow-cljs -> :app

Fire up http://localhost:4200/

### Deployment

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
