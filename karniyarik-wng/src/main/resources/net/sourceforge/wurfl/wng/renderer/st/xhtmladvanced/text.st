$if(component.idOrClassDefined)$
<span$idAndClass(component=component)$>$component.content$</span>
$else$
$component.content$
$endif$