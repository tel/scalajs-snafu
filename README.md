
# Snafu: Explorations of DSLs for SVG charts and graphics

Snafu is a small application exploring a SVG-based diagramming designs.

## Getting started

Snafu works using [Li Haoyi's Workbench](https://github.com/lihaoyi/workbench) 
for snappy hot-reload cylces. If you have `sbt` installed then

```
sbt ~fastOptJS
```

will compile the sources and spin up a development server. From here 
visit `http://localhost:12345/target/scala-2.11/classes/index-dev.html` 
to see Snafu. This page will auto-reload as soon as any Snafu source is 
changed.

## References

- [Daniel James's *Scala's Modular Roots*](https://io.pellucid.com/blog/scalas-modular-roots) 
  describes a means for achieve ML-like modularity in Scala. Signatures,
  structures, and functors are clear guiding principles in Scala's design
  and James shows a few ways to achieve their functionality by cribbing
  ideas from *Scala: the Simple Parts* and elaborating. 
- [Martin Odersky's *Scala: the Simple Parts*](http://www.slideshare.net/Odersky/scala-the-simple-parts)
  was important for showing modularity techniques for Scala in a clean 
  way. Not only is it apparently canonical as coming from Odersky himself,
  it has *very* nice examples like https://gist.github.com/tel/2992dcbbff7af053bc55.
- [Oleg Kiselyov's *Finally Tagless Style*](http://okmij.org/ftp/tagless-final/)
  describes a means for making ASTs and interpreters for them which do 
  not require actually summoning up the intermediate structure. This is
  a very nice mechanism for writing DSLs (though it is, essentially, 
  just a tiny shift in perspective on normal ML representation techniques).
  
