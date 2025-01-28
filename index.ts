(async() => {
  const $: typeof import('bun').$ = await import('bun').then(m => m.$);
  for (const i of Array.from({ length: 15 }).map((_, i) => i + 1))
    await $`mkdir -p week-${i} && cd week-${i} && touch README.md`;
})()